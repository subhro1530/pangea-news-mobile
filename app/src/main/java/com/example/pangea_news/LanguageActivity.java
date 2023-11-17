package com.example.pangea_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Button englishButton = findViewById(R.id.englishButton);
        Button hindiButton = findViewById(R.id.hindiButton);
        ImageButton nextButton = findViewById(R.id.nextButton);

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the selected language to English (You may want to store it globally)
                String selectedLanguage = "English";

                // Example: Show a toast message
                Toast.makeText(LanguageActivity.this, "Selected Language: " + selectedLanguage, Toast.LENGTH_SHORT).show();
            }
        });

        hindiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the selected language to Hindi (You may want to store it globally)
                String selectedLanguage = "Hindi";

                // Example: Show a toast message
                Toast.makeText(LanguageActivity.this, "Selected Language: " + selectedLanguage, Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the home page
                startActivity(new Intent(LanguageActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
