package com.amrendra.gridgame.game;

import com.amrendra.gridgame.board.Board;
import com.amrendra.gridgame.board.TileView;
import com.amrendra.gridgame.bus.BusProvider;
import com.amrendra.gridgame.events.TileViewClickEvent;
import com.amrendra.gridgame.events.TileViewCreatedEvent;
import com.amrendra.gridgame.utils.Debug;
import com.amrendra.gridgame.utils.RandomUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class Game {
    private GameManager mGameManager;
    private Board mBoard;
    private TileView[][] mTilesViewsGrid;

    private boolean mIsGameFinished = false;
    private long mElapsedTime = 0;
    private long mStartTime;

    int score = 0;
    int dimension = 1;


    int seedX = -1, seedY = -1;
    int normalColor = -1;
    int seedColor = -1;

    public Game(GameManager gameManager) {
        mGameManager = gameManager;
        score = 0;
        dimension = 1;
        BusProvider.getInstance().register(this);
    }


    public void startNextLevel() {
        dimension++;
        if (dimension > GameConstants.MAX_DIMENSIONS) {
            dimension = GameConstants.MAX_DIMENSIONS;
        }
        generateNewSeed();
        mBoard = new Board(dimension);
        mTilesViewsGrid = new TileView[dimension][dimension];
        mGameManager.publishNextLevel();
        //mGameManager.publishElapsedTime(mElapsedTime);
    }


    private void incrementScore() {
        score++;
        mGameManager.publishScore();
    }

    public int getScore() {
        return score;
    }

    public Board getBoard() {
        return mBoard;
    }


    public void startTimer() {
        mStartTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        if (mStartTime > 0) {
            mElapsedTime += (System.currentTimeMillis() - mStartTime);

            mStartTime = 0;
        }
    }

    public void unregisterFromEventBus() {
        BusProvider.getInstance().unregister(this);
    }

    public void finishGame() {
        Debug.c();
        unregisterFromEventBus();
        publishGameResult();
    }

    public void publishGameResult() {
        if (!mIsGameFinished) {
            mIsGameFinished = true;
            mGameManager.publishGameFinished();
        }
    }

    public boolean isGameFinished() {
        return mIsGameFinished;
    }

    public long getElapsedTime() {
        // If the timer has been started, then add the time since it was started
        // to the saved elapsed time.
        long additionalRealTime = 0;

        if (mStartTime > 0) {
            additionalRealTime = System.currentTimeMillis() - mStartTime;
        }
        return mElapsedTime + additionalRealTime;
    }


    private void generateNewSeed() {
        seedX = RandomUtils.randInt(0, dimension - 1);
        seedY = RandomUtils.randInt(0, dimension - 1);
        normalColor = RandomUtils.getRandomColor();
        seedColor = RandomUtils.getRandomColor();
        if (seedColor == normalColor) {
            while (seedColor == normalColor) {
                seedColor = RandomUtils.getRandomColor();
            }
        }
        Debug.i("x: " + seedX + " y: " + seedY + " normal: " + normalColor + " seedColor: " + seedColor, false);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onTileCreatedEvent(TileViewCreatedEvent event) {
        TileView tileView = event.getTileView();
        int x = tileView.getXGridCoordinate();
        int y = tileView.getYGridCoordinate();
        if (x == seedX && y == seedY) {
            tileView.setupDrawableBackgrounds(seedColor);
        } else {
            tileView.setupDrawableBackgrounds(normalColor);
        }
        mTilesViewsGrid[y][x] = tileView;

    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onTileClickEvent(TileViewClickEvent event) {
        TileView tileView = event.getTileView();
        int x = tileView.getXGridCoordinate();
        int y = tileView.getYGridCoordinate();
        Debug.i(tileView.toString(), false);
        if (x == seedX && y == seedY) {
            if (!isGameFinished()) {
                incrementScore();
                startNextLevel();
            }
        }
    }
}
