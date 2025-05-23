package com.example.BlockPy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

// Changed import to use correct package
import com.example.BlockPy.AboutFragment;

public class MainMenuActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialize and start background music
        setupBackgroundMusic();

        Button enterButton = findViewById(R.id.button_enter);
        Button aboutButton = findViewById(R.id.button_about);
        Button exitButton = findViewById(R.id.button_exit);

        if (enterButton != null) {
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start HomeActivity
                    Intent intent = new Intent(MainMenuActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (aboutButton != null) {
            aboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show About fragment instead of a Toast
                    showAboutFragment();
                }
            });
        }

        if (exitButton != null) {
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close the app
                    finishAffinity(); // Close this activity and all parent activities
                }
            });
        }
    }

    /**
     * Sets up the background music for the main menu
     */
    private void setupBackgroundMusic() {
        // Create and set up the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.mainmenumusic);
        mediaPlayer.setLooping(true); // Loop the music continuously
        mediaPlayer.setVolume(0.5f, 0.5f); // Set volume (values from 0.0 to 1.0)
        mediaPlayer.start(); // Start playing the music
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume background music when the activity comes back to foreground
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause background music when the activity goes to background
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Shows the About fragment
     */
    public void showAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, aboutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

