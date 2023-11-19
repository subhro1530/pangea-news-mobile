package com.example.pangea_news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Find the ImageView in your layout
        ImageView gifImageView = findViewById(R.id.gifImageView);

        // Load and display the GIF using Glide with improved loading speed
        Glide.with(this)
                .asGif()
                .load(R.raw.pangea)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Use RESOURCE to load from the local resource without caching
                .into(gifImageView);

        // Open the HomeActivity after the GIF animation is completed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openLanguageActivity();
                // Finish the current activity
                finish();
            }
        }, getGifDuration(R.raw.pangea));
    }

    private void openLanguageActivity() {
        // Start the LanguageActivity
        Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
        startActivity(intent);
    }

    //  For Testing
//    private void openSettingsActivity() {
//        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//        startActivity(intent);
//    }

    // Helper method to get the duration of the GIF
    private int getGifDuration(int gifResource) {
        try {
            android.graphics.Movie movie = android.graphics.Movie.decodeStream(getResources().openRawResource(gifResource));
            return movie.duration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

