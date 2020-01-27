package com.rickbala.myfrickinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Grain {

    int x;
    int y;
    int velocity;
    Bitmap bitmap;

    public Grain(Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grain);
        x = GameView.dWidth / 2 - getGrainWidth() / 2;
        y = GameView.dHeight - GameView.handHeight - getGrainHeight() / 2;
        velocity = 50;
    }

    public int getGrainWidth(){
        return bitmap.getWidth();
    }

    public int getGrainHeight(){
        return bitmap.getHeight();
    }
}
