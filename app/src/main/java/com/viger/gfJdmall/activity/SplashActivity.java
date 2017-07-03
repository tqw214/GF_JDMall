package com.viger.gfJdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.viger.gfJdmall.R;

/**
 * Created by Administrator on 2017/5/8.
 */

public class SplashActivity extends BaseActivity {

    private AlphaAnimation mAnim;
    private ImageView mLogo;

    @Override
    protected void initView() {
        mLogo = (ImageView) findViewById(R.id.logo_iv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initAnim();
    }

    private void initAnim() {
        mAnim = new AlphaAnimation(0.0f, 1.0f);
        mAnim.setDuration(1000);
        mAnim.setFillAfter(true);
        mLogo.startAnimation(mAnim);
        mAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //startMainActivity();
                startLoginActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
