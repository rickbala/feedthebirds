package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameOver extends Activity {

    TextView scoreLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        int score = getIntent().getExtras().getInt("score");
        scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + score);
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, StartGame.class);
        startActivity(intent);
        finish();
    }

}
