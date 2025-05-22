package com.example.BlockPy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

// Changed import to use correct package
import com.example.BlockPy.AboutFragment;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

