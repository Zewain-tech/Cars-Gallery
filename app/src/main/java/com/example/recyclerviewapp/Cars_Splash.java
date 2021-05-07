package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Cars_Splash extends AppCompatActivity {
    ImageView Splash;
    TextView TVSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars__splash);
         Splash=findViewById(R.id.iv_splash);
         TVSplash=findViewById(R.id.tv_splash);
        YoYo.with(Techniques.Shake).duration(4000).playOn(Splash);
       // YoYo.with(Techniques.FadeIn).duration(4000).playOn(TVSplash);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent=new Intent(Cars_Splash.this,MainActivity.class);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        YoYo.with(Techniques.Shake).duration(4000).playOn(Splash);
        // YoYo.with(Techniques.FadeIn).duration(4000).playOn(TVSplash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(Cars_Splash.this, MainActivity.class);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.stop();
    }
}
