package com.cnam.tarmin.drawings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Intent drawing1Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawing1Activity = new Intent(this, Drawing1.class);
        startActivity(drawing1Activity);
    }
}
