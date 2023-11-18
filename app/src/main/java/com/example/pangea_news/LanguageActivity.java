package com.example.pangea_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Button englishButton = findViewById(R.id.englishButton);
        Button hindiButton = findViewById(R.id.hindiButton);

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the selected language to English
                String selectedLanguage = "en";

                // Example: Show a toast message
                Toast.makeText(LanguageActivity.this, "Selected Language: English", Toast.LENGTH_SHORT).show();

                // Start HomeActivity and pass the selected language
                startHomeActivity(selectedLanguage);
            }
        });

        hindiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the selected language to Hindi
                String selectedLanguage = "hi";

                // Example: Show a toast message
                Toast.makeText(LanguageActivity.this, "Selected Language: Hindi", Toast.LENGTH_SHORT).show();

                // Start HomeActivity and pass the selected language
                startHomeActivity(selectedLanguage);
            }
        });
    }

    private void startHomeActivity(String selectedLanguage) {
        // Start HomeActivity and pass the selected language
        Intent intent = new Intent(LanguageActivity.this, HomeActivity.class);
        intent.putExtra("SELECTED_LANGUAGE", selectedLanguage);
        startActivity(intent);
        finish();
    }
}
