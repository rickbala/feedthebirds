package com.rickbala.myfrickinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Bird{

    Bitmap[] bitmaps = new Bitmap[18];
    int birdX;
    int birdY;
    int velocity;
    int birdFrame;
    Random random;
    int numberOfFrames;

    public Bird(Context context){
        numberOfFrames = 18;
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_00);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_01);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_02);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_03);
        bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_04);
        bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_05);
        bitmaps[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_06);
        bitmaps[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_07);
        bitmaps[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_08);
        bitmaps[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_09);
        bitmaps[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_10);
        bitmaps[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_11);
        bitmaps[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_12);
        bitmaps[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_13);
        bitmaps[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_14);
        bitmaps[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_15);
        bitmaps[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_16);
        bitmaps[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_17);
        random = new Random();
        resetPosition(1);
    }

    public Bitmap getBitmap(){
        return bitmaps[birdFrame];
    }

    public int getWidth(){
        return bitmaps[0].getWidth();
    }

    public int getHeight(){
        return bitmaps[0].getHeight();
    }

    public void resetPosition(int count){
        birdX = GameView.dWidth + random.nextInt(1000);
        birdY = random.nextInt(500);
        velocity = 7 + random.nextInt(8) * (count/17);
        birdFrame = 0;
    }

}
