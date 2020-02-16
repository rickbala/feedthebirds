package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameOver extends Activity {

    TextView scoreLabel, highScoreLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        int score = getIntent().getExtras().getInt("score");

        SharedPreferences pref = getSharedPreferences("MyFreeBridsPref", 0);
        int highScore = pref.getInt("highScore", 0);
        SharedPreferences.Editor editor = pref.edit();
        if (score > highScore){
            highScore = score;
            editor.putInt("highScore", highScore);
            editor.commit();
        }

        highScoreLabel = findViewById(R.id.highScoreLabel);
        highScoreLabel.setText("High Score: " + highScore);

        scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + score);
    }

    public void restart(View view){
        Intent toStopBGMusic = new Intent(this, MusicService.class);
        stopService(toStopBGMusic);

        //kill previous game
        ((Activity) view.getContext()).finish();

        //start game again
        Intent intent = new Intent(GameOver.this, StartGame.class);
        startActivity(intent);
        finish();
    }

}
