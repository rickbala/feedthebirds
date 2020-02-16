package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class StartGame extends Activity {

    GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //use starg_game.xml as layout
        setContentView(R.layout.start_game);

        //instantiate main game view
        gameView = new GameView(this);

        //assign game view to the layout below the ad
        FrameLayout gameFrameLayout = (FrameLayout)findViewById(R.id.gameFrameLayout);
        gameFrameLayout.addView(gameView);
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent toStopBGMusic = new Intent(this, MusicService.class);
        stopService(toStopBGMusic);
    }
}
