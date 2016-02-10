package com.amrendra.gridgame.game;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public interface GameListener {
    void updateScore(int score);
    void onGameFinished();
    void updateTimeElapsed(long timeElapsed);
}
