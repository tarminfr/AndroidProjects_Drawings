package com.cnam.tarmin.drawings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingScreen extends SurfaceView implements Runnable {

    static final String CLASS_TAG = "DrawingScreen";
    Thread thread;
    SurfaceHolder surfaceHolder;
    boolean availableFlag;
    Bitmap redBall;
    float x, y, vx, vy, vmax;

    public DrawingScreen(Context context) {
        super(context);
        surfaceHolder = getHolder();
        redBall = BitmapFactory.decodeResource(getResources(), R.drawable.bille);
        vmax = 300;
    }

    @Override
    public void run() {
        while(availableFlag) {
            //draw
            //Log.i(CLASS_TAG, "x=" + x + ", y=" + y);
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawARGB(255, 150, 150, 10);
            canvas.drawBitmap(redBall, x - (redBall.getWidth() / 2), y - (redBall.getHeight() / 2), null);
            surfaceHolder.unlockCanvasAndPost(canvas);
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
