package com.example.pangea_news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.AppCompatImageButton;

import com.bumptech.glide.Glide;
import com.example.pangea_news.network.Article;
import com.example.pangea_news.network.NewsApi;
import com.example.pangea_news.network.NewsResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final String API_KEY = "f5b874a8cc8c944a5ef4fcf58b8a59b9"; // Replace with your actual GNews API key
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NewsApi newsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView languageButton = findViewById(R.id.languageButton);

        ImageButton uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUploadActivity();
            }
        });

        // Set OnClickListener for the language button
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the LanguageActivity when the language button is clicked
                Intent intent = new Intent(HomeActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });

        // Find the Settings button by ID
        ImageButton settingsButton = findViewById(R.id.settingsButton);

        // Set click listener for the Settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open SettingsActivity
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Retrieve selected language and country from Intent
        String selectedLanguage = getIntent().getStringExtra("SELECTED_LANGUAGE");
        String selectedCountry = getIntent().getStringExtra("SELECTED_COUNTRY");

        // Set up the sidebar toggle
        setUpSidebar();

        // Initialize Retrofit and NewsApi
        initializeRetrofit();

        // Fetch and display news on initial load
        fetchAndDisplayNews(selectedLanguage, selectedCountry);

        // Refresh Button
        AppCompatImageButton refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> {
            // Retrieve selected language and country from Intent
            String refreshedLanguage = getIntent().getStringExtra("SELECTED_LANGUAGE");
            String refreshedCountry = getIntent().getStringExtra("SELECTED_COUNTRY");

            // Use the selected language and country in your API request
            fetchAndDisplayNews(refreshedLanguage, refreshedCountry);
        });
    }

    private void openUploadActivity() {
        // Open the UploadActivity when the upload button is clicked
        Intent intent = new Intent(HomeActivity.this, UploadActivity.class);
        startActivity(intent);
    }


    private void setUpSidebar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        // Set up the sidebar toggle
        ImageView sidebarImageView = findViewById(R.id.sidebarImageView);
        sidebarImageView.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_top_news:
                    fetchAndDisplayNews(getIntent().getStringExtra("SELECTED_LANGUAGE"), getIntent().getStringExtra("SELECTED_COUNTRY"));
                    break;
                case R.id.nav_world:
                    fetchAndDisplayNews(getIntent().getStringExtra("SELECTED_LANGUAGE"), "world");
                    break;
                case R.id.nav_india:
                    fetchAndDisplayNews(getIntent().getStringExtra("SELECTED_LANGUAGE"), "in");
                    break;
                // Add cases for other menu items
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void initializeRetrofit() {
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gnews.io/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the NewsApi service
        newsApi = retrofit.create(NewsApi.class);
    }

    private void fetchAndDisplayNews(String language, String country) {
        // Make the network request for top headlines
        Call<NewsResponse> call = newsApi.getTopHeadlines("general", language, country, 10, API_KEY);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    List<Article> articles = response.body().getArticles();
                    displayNews(articles);
                } else {
                    Log.e("HomeActivity", "API Error: " + response.code());
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("HomeActivity", "Network Error: " + t.getMessage());
                t.printStackTrace();
                // Handle failure
            }
        });
    }

    private void displayNews(List<Article> articles) {
        ScrollView newsScrollView = findViewById(R.id.newsScrollView);
        LinearLayout newsContainer = newsScrollView.findViewById(R.id.newsContainer);

        newsContainer.removeAllViews(); // Clear existing views before adding new ones

        for (Article article : articles) {
            View newsItem = getLayoutInflater().inflate(R.layout.news_item, null);

            // Set news title and description dynamically
            TextView titleTextView = newsItem.findViewById(R.id.newsTitleTextView);
            TextView descriptionTextView = newsItem.findViewById(R.id.newsDescriptionTextView);
            ImageView imageView = newsItem.findViewById(R.id.newsImageView);

            titleTextView.setText(article.getTitle());
            descriptionTextView.setText(article.getDescription());

            // Load and display the image using Glide
            Glide.with(this)
                    .load(article.getImageUrl())
                    .into(imageView);

            newsContainer.addView(newsItem);
        }
    }
}
