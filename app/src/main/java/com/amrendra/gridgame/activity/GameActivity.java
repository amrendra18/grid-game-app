package com.amrendra.gridgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amrendra.gridgame.R;
import com.amrendra.gridgame.board.Board;
import com.amrendra.gridgame.board.BoardLayoutView;
import com.amrendra.gridgame.game.GameConstants;
import com.amrendra.gridgame.game.GameListener;
import com.amrendra.gridgame.game.GameManager;
import com.amrendra.gridgame.utils.Debug;
import com.amrendra.gridgame.utils.SharedPreferenceMgr;

import java.util.Timer;
import java.util.TimerTask;

/***
 * Activity only interacts with GameManager
 * Similary, Game only interacts with GameManager
 */
public class GameActivity extends AppCompatActivity implements GameListener {

    private GameManager mGameManager;
    private int mDimensionsX = Board.DEFAULT_DIMENSION_X;

    private int mDimensionsY = Board.DEFAULT_DIMENSION_Y;

    private Timer mTimer;

    private BoardLayoutView mBoardLayoutView;
    private TextView scoreTextView;
    private TextView timeTextView;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
        setupGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViews() {
        mBoardLayoutView = (BoardLayoutView) findViewById(R.id.board_layout_view);
        scoreTextView = (TextView) findViewById(R.id.score_tv);
        timeTextView = (TextView) findViewById(R.id.timer_tv);
    }

    private void setupGame() {
        Debug.c();
        mGameManager = new GameManager(mBoardLayoutView, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.c();
        if (mGameManager != null) {
            startTimer();
        }
    }

    private void startTimer() {
        Debug.c();
        if (mGameManager != null && !mGameManager.isGameFinished()) {
            // Game holds the timer
            // so need to start Game timer via GameManager
            mGameManager.startTimer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateTimeElapsed(mGameManager.getElapsedTime());
                        }
                    });
                }
            };

            mTimer = new Timer();

            mTimer.schedule(timerTask, 0, 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        Debug.c();
        if (mGameManager != null && mTimer != null) {
            mGameManager.stopTimer();
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void updateTimeElapsed(long elapsedTime) {
        int elapsedTimeInSeconds = (int) elapsedTime / 1000;
        int remainingTime = (GameConstants.GAME_DURATION - elapsedTimeInSeconds);
        String toShow;
        if (remainingTime > 0) {
            toShow = getResources().getString(R.string.time) + " " + remainingTime;
        } else {
            toShow = "Time's Up";
            mGameManager.finishGame();
        }
        Debug.i(String.valueOf(elapsedTimeInSeconds), false);
        timeTextView.setText(toShow);
    }

    @Override
    public void onGameFinished() {
        Debug.c();
        stopTimer();
        Debug.showLong("Game Finished", this);
        showScoreActivity();
    }

    @Override
    public void updateScore(int score) {
        scoreTextView.setText(getResources().getString(R.string.score) + " " + score);
        this.score = score;
    }

    private void showScoreActivity(){
        Intent intent = new Intent(GameActivity.this, StartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(GameConstants.CURRENT_SCORE, score);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }



}
