package com.rickbala.myfrickinggame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    Bitmap background0,background1,background2,background3,background4,background5,background6,background7,background8;
    Bitmap hand;
    Rect rect;

    ArrayList<Bird> birds;
    ArrayList<Butterfly> butterflies;
    ArrayList<Grain> grains;
    ArrayList<Hearted> hearteds;

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
    int bg = 0;
    int next = 0;
    int gameover = 0;

    Paint scorePaint;
    final int TEXT_SIZE = 60;

    Paint healthPaint;
    int life = 10;

    public GameView(Context context){
        super(context);
        this.context = context; //?

        background0 = BitmapFactory.decodeResource(getResources(), R.drawable.background0);
        background1 = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
        background2 = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        background3 = BitmapFactory.decodeResource(getResources(), R.drawable.background3);
        background4 = BitmapFactory.decodeResource(getResources(), R.drawable.background4);
        background5 = BitmapFactory.decodeResource(getResources(), R.drawable.background5);
        background6 = BitmapFactory.decodeResource(getResources(), R.drawable.background6);
        background7 = BitmapFactory.decodeResource(getResources(), R.drawable.background7);
        background8 = BitmapFactory.decodeResource(getResources(), R.drawable.background8);

        hand = BitmapFactory.decodeResource(getResources(), R.drawable.hand);

        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        hearteds = new ArrayList<>();

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

        sp = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
        give = sp.load(context, R.raw.give, 1);
        take = sp.load(context, R.raw.take, 1);
        next = sp.load(context, R.raw.next, 1);
        gameover = sp.load(context, R.raw.gameover, 1);
        bg = sp.load(context, R.raw.bg, 1);

        scorePaint = new Paint();
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        healthPaint = new Paint();
        healthPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (count > 125) canvas.drawBitmap(background8, null, rect, null);
        else if (count > 110) canvas.drawBitmap(background7, null, rect, null);
        else if (count > 90) canvas.drawBitmap(background6, null, rect, null);
        else if (count > 75) canvas.drawBitmap(background5, null, rect, null);
        else if (count > 60) canvas.drawBitmap(background4, null, rect, null);
        else if (count > 45) canvas.drawBitmap(background3, null, rect, null);
        else if (count > 30) canvas.drawBitmap(background2, null, rect, null);
        else if (count > 15) canvas.drawBitmap(background1, null, rect, null);
        else if (count >= 0) canvas.drawBitmap(background0, null, rect, null);

        for (Bird b:birds){
            canvas.drawBitmap(b.getBitmap(), b.birdX, b.birdY, null);
            b.birdFrame++;
            if (b.birdFrame > b.numberOfFrames-1){
                b.birdFrame = 0;
            }
            b.birdX -= b.velocity;
            if (b.birdX < -b.getWidth()){
                b.resetPosition(count);
                life--;
                if (life == 0){
                    sp.play(gameover, 1, 1, 0, 0, 1);
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", (count * 10));
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
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
                b.resetPosition(count);
                life--;
                if (life == 0){
                    sp.play(gameover, 1, 1, 0, 0, 1);
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", (count * 10));
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
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

                    Hearted hearted = new Hearted(context);
                    hearted.heartedX = birds.get(0).birdX + birds.get(0).getWidth() / 2 - hearted.getHeartedWidth() / 2;
                    hearted.heartedY = birds.get(0).birdY + birds.get(0).getHeight() / 2 - hearted.getHeartedHeight() / 2;
                    hearteds.add(hearted);

                    birds.get(0).resetPosition(count);
                    count++;
                    playNextLevel();
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= birds.get(1).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        birds.get(1).birdX + birds.get(1).getWidth()) &&
                        grains.get(i).y >= birds.get(1).birdY && grains.get(i).y <= (birds.get(1).birdY + birds.get(1).getHeight())) {

                    Hearted hearted = new Hearted(context);
                    hearted.heartedX = birds.get(1).birdX + birds.get(1).getWidth() / 2 - hearted.getHeartedWidth() / 2;
                    hearted.heartedY = birds.get(1).birdY + birds.get(1).getHeight() / 2 - hearted.getHeartedHeight() / 2;
                    hearteds.add(hearted);

                    birds.get(1).resetPosition(count);
                    count++;
                    playNextLevel();
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= butterflies.get(0).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        butterflies.get(0).birdX + butterflies.get(0).getWidth()) &&
                        grains.get(i).y >= butterflies.get(0).birdY && grains.get(i).y <= (butterflies.get(0).birdY + butterflies.get(0).getHeight())) {

                    Hearted hearted = new Hearted(context);
                    hearted.heartedX = butterflies.get(0).birdX + butterflies.get(0).getWidth() / 2 - hearted.getHeartedWidth() / 2;
                    hearted.heartedY = butterflies.get(0).birdY + butterflies.get(0).getHeight() / 2 - hearted.getHeartedHeight() / 2;
                    hearteds.add(hearted);

                    butterflies.get(0).resetPosition(count);
                    count++;
                    playNextLevel();
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }else if (grains.get(i).x >= butterflies.get(1).birdX && (grains.get(i).x + grains.get(i).getGrainWidth() <=
                        butterflies.get(1).birdX + butterflies.get(1).getWidth()) &&
                        grains.get(i).y >= butterflies.get(1).birdY && grains.get(i).y <= (butterflies.get(1).birdY + butterflies.get(1).getHeight())) {

                    Hearted hearted = new Hearted(context);
                    hearted.heartedX = butterflies.get(1).birdX + butterflies.get(1).getWidth() / 2 - hearted.getHeartedWidth() / 2;
                    hearted.heartedY = butterflies.get(1).birdY + butterflies.get(1).getHeight() / 2 - hearted.getHeartedHeight() / 2;
                    hearteds.add(hearted);

                    butterflies.get(1).resetPosition(count);
                    count++;
                    playNextLevel();
                    grains.remove(i);
                    if (take != 0){
                        sp.play(take, 1, 1, 0, 0, 1);
                    }
                }

            }else{
                grains.remove(i);
            }
        }

        for (int j = 0; j<hearteds.size(); j++){
            canvas.drawBitmap(hearteds.get(j).getHearted(hearteds.get(j).heartedFrame), hearteds.get(j).heartedX,
                    hearteds.get(j).heartedY, null);
            hearteds.get(j).heartedFrame++;
            if (hearteds.get(j).heartedFrame > 8){
                hearteds.remove(j);
            }
        }

        canvas.drawBitmap(hand, (dWidth/2 - handWidth/2), dHeight - handHeight, null);
        canvas.drawText("Score: " + (count * 10), 0 , TEXT_SIZE, scorePaint);
        canvas.drawRect(dWidth - 110, 10, dWidth - 110 + 10*life, TEXT_SIZE, healthPaint);

        handler.postDelayed(runnable, UPDATE_MILIS); //delay, increases performance
    }

    public void playNextLevel(){
        if (next != 0)
            if (count == 15 || count == 30 || count == 45 || count == 60 || count == 75 ||
                count == 90 || count == 105 || count == 120 )
                sp.play(next, 1, 1, 1, 0, 1);
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
