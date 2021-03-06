package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void startGame(View v){
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent toStopBGMusic = new Intent(this, MusicService.class);
        stopService(toStopBGMusic);
    }
}
