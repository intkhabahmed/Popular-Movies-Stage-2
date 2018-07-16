package com.intkhabahmed.popularmoviesstage2.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.intkhabahmed.popularmoviesstage2.R;
import com.intkhabahmed.popularmoviesstage2.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding mSplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        mSplashScreenBinding.curtainIv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.exit_from_top));
        mSplashScreenBinding.splashScreenLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
