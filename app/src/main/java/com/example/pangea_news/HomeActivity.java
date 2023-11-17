package com.example.pangea_news;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pangea_news.network.Article;
import com.example.pangea_news.network.NewsApi;
import com.example.pangea_news.network.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final String API_KEY = "17e5786f01adec6fc3b5c4421cf147d1"; // Replace with your actual GNews API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gnews.io/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the NewsApi service
        NewsApi newsApi = retrofit.create(NewsApi.class);

        // Make the network request for top headlines
        Call<NewsResponse> call = newsApi.getTopHeadlines("general", "en", "in", 10, API_KEY);

        // Print the full URL for testing
        String url = call.request().url().toString();
        Log.d("Full URL", url);

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
