package com.cnam.tarmin.drawings;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawingScreen2 extends SurfaceView implements Runnable {

    static final String CLASS_TAG = "DrawingScreen";
    Thread thread;
    SurfaceHolder surfaceHolder;
    Activity hostActivity;
    boolean availableFlag;
    Bitmap redBall;
    float x, y, vx, vy, vmax;

    public DrawingScreen2(Context context) {
        super(context);
        surfaceHolder = getHolder();
        redBall = BitmapFactory.decodeResource(getResources(), R.drawable.bille);
        vmax = 300;

        hostActivity = (Activity) context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 150, 150, 10);
        canvas.drawBitmap(redBall, x - (redBall.getWidth() / 2), y - (redBall.getHeight() / 2), null);
    }

    @Override
    public void run() {
        while(availableFlag) {
            //recuperer le call du sensor et faire les calculs, puis appelez invalide par le uiThread
            processPosition();

            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DrawingScreen2.this.invalidate();
                }
            });
        }
    }

    public void pause() {
        availableFlag = false;

        while (true) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }

        thread = null;
    }

    public void resume() {
        availableFlag = true;
        thread = new Thread(this);
        thread.start();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void addVelocity(float vx, float vy) {
        this.vx += vx;
        this.vy += vy;

        if(this.vx > vmax) {
            this.vx = vmax;
        }
        if(this.vy > vmax) {
            this.vy = vmax;
        }
    }

    public void processPosition() {

        if(x < 0 || x > getMeasuredWidth()) {
            vx = -vx;
        }
        if(y < 0 || y > getMeasuredHeight()) {
            vy = -vy;
        }

        //-= car pour faire allez la balle vers la gauche on penche vers la gauche
        x -= vx;
        y += vy;

        Log.i(CLASS_TAG, "processPosition called: x=" + x + ", y=" + y + ", vx=" + vx + ", vy=" + vy);
    }

}
