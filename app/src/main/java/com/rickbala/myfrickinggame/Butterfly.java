package com.rickbala.myfrickinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Butterfly extends Bird {

    Bitmap[] bitmaps = new Bitmap[7];

    public Butterfly(Context context) {
        super(context);
        numberOfFrames = 7;
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_0);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_1);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_2);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_3);
        bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_4);
        bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_5);
        bitmaps[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly_6);
        resetPosition();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmaps[birdFrame];
    }

    @Override
    public int getWidth() {
        return bitmaps[0].getWidth();
    }

    @Override
    public int getHeight() {
        return bitmaps[0].getHeight();
    }

    @Override
    public void resetPosition() {
        birdX = -(200 + random.nextInt(700));
        birdY = random.nextInt(400);
        velocity = 5 + random.nextInt(8);
    }
}
