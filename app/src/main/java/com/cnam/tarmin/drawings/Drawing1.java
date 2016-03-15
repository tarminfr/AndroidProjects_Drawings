package com.cnam.tarmin.drawings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Drawing1 extends AppCompatActivity implements View.OnTouchListener {

    DrawingScreen drawingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.drawing1);

        drawingScreen = new DrawingScreen(this);
        setContentView(drawingScreen);
        drawingScreen.setOnTouchListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        drawingScreen.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        drawingScreen.pause();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawingScreen.resume();
                break;
            case MotionEvent.ACTION_UP:
                drawingScreen.pause();
                break;
            case MotionEvent.ACTION_MOVE:
                drawingScreen.setPosition(event.getX(), event.getY());
                break;
        }
        */
        drawingScreen.setPosition(event.getX(), event.getY());

        return true;
    }
}
