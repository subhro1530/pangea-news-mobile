package com.example.pangea_news;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

    private static final String API_KEY = "17e5786f01adec6fc3b5c4421cf147d1"; // Replace with your actual GNews API key
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NewsApi newsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve selected language from Intent
        String selectedLanguage = getIntent().getStringExtra("SELECTED_LANGUAGE");

        // Set up the sidebar toggle
        setUpSidebar();

        // Initialize Retrofit and NewsApi
        initializeRetrofit();

        // Fetch and display news on initial load
        fetchAndDisplayNews(selectedLanguage);

        // Refresh Button
        Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> fetchAndDisplayNews(selectedLanguage));
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
                    // Handle Top News click
                    break;
                case R.id.nav_world:
                    // Handle World click
                    break;
                case R.id.nav_india:
                    // Handle India click
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

    private void fetchAndDisplayNews(String language) {
        // Make the network request for top headlines
        Call<NewsResponse> call = newsApi.getTopHeadlines("general", language, "in", 10, API_KEY);

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
        LinearLayout newsContainer = findViewById(R.id.newsContainer);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshButton) {
            // Retrieve selected language from Intent
            String selectedLanguage = getIntent().getStringExtra("SELECTED_LANGUAGE");

            // Use the selected language in your API request
            fetchAndDisplayNews(selectedLanguage);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
