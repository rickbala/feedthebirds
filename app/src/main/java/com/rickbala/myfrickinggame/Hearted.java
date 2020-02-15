package com.rickbala.myfrickinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Hearted {

    Bitmap hearted[] = new Bitmap[9];
    int heartedFrame=0;
    int heartedX, heartedY;

    public Hearted(Context context) {
        hearted[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart0);
        hearted[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart1);
        hearted[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart2);
        hearted[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart3);
        hearted[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart4);
        hearted[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart5);
        hearted[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart6);
        hearted[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart7);
        hearted[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart8);
    }

    public Bitmap getHearted(int heartedFrame) {
        return hearted[heartedFrame];
    }

    public int getHeartedWidth(){
        return hearted[heartedFrame].getWidth();
    }

    public int getHeartedHeight(){
        return hearted[0].getHeight();
    }

}
