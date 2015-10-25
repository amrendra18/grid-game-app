package com.amrendra.gridgame.game;

import com.amrendra.gridgame.board.BoardLayoutView;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class GameManager {
    private GameListener mListener;
    private BoardLayoutView mBoardLayoutView;
    private Game mGame;

    public GameManager(BoardLayoutView boardLayoutView, GameListener listener) {
        mListener = listener;
        if (mGame != null) {
            mGame.unregisterFromEventBus();
        }
        mBoardLayoutView = boardLayoutView;
        mGame = new Game(this);
        mGame.startNextLevel();
    }

    public void publishNextLevel(){
        mBoardLayoutView.setupBoard(mGame.getBoard());
    }


    // Methods Implemented by our calling activity
    public void publishScore(){
        mListener.updateScore(mGame.getScore());
    }
    public void publishGameFinished() {
        mListener.onGameFinished();
    }

    public void publishElapsedTime() {
        mListener.updateTimeElapsed(mGame.getElapsedTime());
    }
    // End methods implemented by our activity


    // Delegate methods to Game object
    public void finishGame() {
        mGame.finishGame();
    }

    public void startTimer() {
        mGame.startTimer();
    }

    public void stopTimer() {
        mGame.stopTimer();
    }

    public long getElapsedTime() {
        return mGame.getElapsedTime();
    }

    public boolean isGameFinished() {
        return mGame.isGameFinished();
    }
}
