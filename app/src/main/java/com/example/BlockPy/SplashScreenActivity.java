package com.example.BlockPy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Get references to views
        ImageView splashLogo = findViewById(R.id.splash_logo);
        TextView appNameText = findViewById(R.id.app_name_text);
        ProgressBar progressBar = findViewById(R.id.splash_progress);

        // Load and start animation for the logo
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_and_fade_in);
        splashLogo.startAnimation(logoAnimation);

        // Set up a listener for when the logo animation ends
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Not needed
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Show app name with animation after logo animation finishes
                appNameText.setVisibility(View.VISIBLE);
                Animation textAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.slide_up_and_fade_in);
                appNameText.startAnimation(textAnimation);

                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                Animation progressAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.slide_up_and_fade_in);
                progressBar.startAnimation(progressAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Not needed
            }
        });

        // Delay to navigate to main menu
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            // Optional: Add a fade transition
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, SPLASH_DURATION);
    }
}
