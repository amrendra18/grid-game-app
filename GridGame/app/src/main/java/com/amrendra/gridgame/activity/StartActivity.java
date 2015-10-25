package com.amrendra.gridgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amrendra.gridgame.R;
import com.amrendra.gridgame.game.GameConstants;
import com.amrendra.gridgame.utils.SharedPreferenceMgr;

public class StartActivity extends AppCompatActivity {

    TextView highScoreTextView;
    Button startGameButton;
    TextView scoreTextView;
    int lastScore = 0;
    int highScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



        setupViews();
        setupListener();
    }


    private void setupViews() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            lastScore = bundle.getInt(GameConstants.CURRENT_SCORE, 0);
        }
        highScore = SharedPreferenceMgr.getInstance(this).readValue(GameConstants.HIGH_SCORE);

        highScore = updateHighScoreIfNeeded(lastScore, highScore);
        highScoreTextView = (TextView) findViewById(R.id.high_score_button);
        highScoreTextView.setText(getResources().getString(R.string.highscore) + " " + highScore);
        if (highScore == 0) {
            highScoreTextView.setVisibility(View.INVISIBLE);
        } else {
            highScoreTextView.setVisibility(View.VISIBLE);
        }


        scoreTextView = (TextView) findViewById(R.id.current_score);
        scoreTextView.setText(getResources().getString(R.string.score) + " " + lastScore);
        if (lastScore == 0) {
            scoreTextView.setVisibility(View.INVISIBLE);
        } else {
            scoreTextView.setVisibility(View.VISIBLE);
        }



        startGameButton = (Button) findViewById(R.id.start_game_button);

    }

    private void setupListener() {
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity();
            }
        });
    }

    private void startGameActivity() {
        Intent intent = new Intent(StartActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    private int updateHighScoreIfNeeded(int score, int highscore){
        if(score > highScore){
            SharedPreferenceMgr.getInstance(this).writeValue(GameConstants.HIGH_SCORE, score);
            highscore = score;
        }
        return highscore;
    }

}
