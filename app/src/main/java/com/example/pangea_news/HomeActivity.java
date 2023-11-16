package com.example.pangea_news;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements NewsApiTask.NewsApiListener {

    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchQuery = searchEditText.getText().toString();

        if (!searchQuery.isEmpty()) {
            new NewsApiTask(this).execute(searchQuery);
        }
    }

    @Override
    public void onNewsFetched(String result) {
        // Handle news data
    }
}
