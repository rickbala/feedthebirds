package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View{

    Bitmap background;
    Bitmap hand;
    Rect rect;

    ArrayList<Bird> birds;
    ArrayList<Butterfly> butterflies;
    ArrayList<Grain> grains;

    int numberOfBirds;
    int numberOfButterflies;
    int numberOfGrains;

    Handler handler;
    Runnable runnable;
    int handWidth;

    static int dWidth;
    static int dHeight;
    static final long UPDATE_MILIS = 20;
    static int handHeight;

    Context context;

    int count = 0;

    SoundPool sp;
    int give = 0;
    int take = 0;

    public GameView(Context context){
        super(context);
        this.context = context; //?

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        hand = BitmapFactory.decodeResource(getResources(), R.drawable.hand);

        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);

        birds = new ArrayList<>();
        numberOfBirds = 2;
        for (int i=0; i<numberOfBirds; i++){
            Bird bird = new Bird(context);
            birds.add(bird);
        }

        butterflies = new ArrayList<>();
        numberOfButterflies = 2;
        for (int i=0; i<numberOfButterflies; i++){
            Butterfly butterfly = new Butterfly(context);
            butterflies.add(butterfly);
        }

        numberOfGrains = 3;
        grains = new ArrayList<>(); // will be populated onTouchEvent

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        handWidth = hand.getWidth();
        handHeight = hand.getHeight();

        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        give = sp.load(context, R.raw.give, 1);
        take = sp.load(context, R.raw.take, 1);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rect, null);

        for (Bird b:birds){
            canvas.drawBitmap(b.getBitmap(), b.birdX, b.birdY, null);
            b.birdFrame++;
            if (b.birdFrame > b.numberOfFrames-1){
                b.birdFrame = 0;
            }
            b.birdX -= b.velocity;
            if (b.birdX < -b.getWidth()){
                b.resetPosition();
            }
        }

        for (Butterfly b:butterflies){
            canvas.drawBitmap(b.getBitmap(), b.birdX, b.birdY, null);
            b.birdFrame++;
            if (b.birdFrame > b.numberOfFrames-1){
                b.birdFrame = 0;
            }
            b.birdX += b.velocity;
            if (b.birdX > dWidth + b.getWidth()){
                b.resetPosition();
            }
        }

        //we use for int=0 type of loop because there is removal of elements at high runtime speed
        for (int i=0; i<grains.size(); i++){ //the size is always changing!
            if(grains.get(i).y > -grains.get(i).getGrainHeight()) {

                grains.get(i).y -= grains.get(i).velocity;
                canvas.drawBitmap(grains.get(i).bitmap, grains.get(i).x, grains.get(i).y, null);

                if (grains.get(i).x >= birds.get(0).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        birds.get(0).birdX + birds.get(0).getWidth()) &&
                        grains.get(i).y >= birds.get(0).birdY && grains.get(i).y <= (birds.get(0).birdY + birds.get(0).getHeight())) {
                    birds.get(0).resetPosition();
                    count++;
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= birds.get(1).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        birds.get(1).birdX + birds.get(1).getWidth()) &&
                        grains.get(i).y >= birds.get(1).birdY && grains.get(i).y <= (birds.get(1).birdY + birds.get(1).getHeight())) {
                    birds.get(1).resetPosition();
                    count++;
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= butterflies.get(0).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        butterflies.get(0).birdX + butterflies.get(0).getWidth()) &&
                        grains.get(i).y >= butterflies.get(0).birdY && grains.get(i).y <= (butterflies.get(0).birdY + butterflies.get(0).getHeight())) {
                    butterflies.get(0).resetPosition();
                    count++;
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= butterflies.get(1).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        butterflies.get(1).birdX + butterflies.get(1).getWidth()) &&
                        grains.get(i).y >= butterflies.get(1).birdY && grains.get(i).y <= (butterflies.get(1).birdY + butterflies.get(1).getHeight())) {
                    butterflies.get(1).resetPosition();
                    count++;
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }

            }else{
                grains.remove(i);
            }
        }

        canvas.drawBitmap(hand, (dWidth/2 - handWidth/2), dHeight - handHeight, null);
        handler.postDelayed(runnable, UPDATE_MILIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (event.getY() > dHeight/2) {
                Log.i("Action", "screen was touched!");
                if (grains.size() < numberOfGrains){
                    Grain g = new Grain(context);
                    grains.add(g);
                    if (give != 0){
                        sp.play(give, 1, 1, 0, 0, 1);
                    }
                }
            }
        }
        return true;
    }
}
