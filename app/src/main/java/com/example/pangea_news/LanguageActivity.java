package com.example.pangea_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        // Language Spinner
        Spinner languageSpinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(
                this, R.array.languages, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        // Country Spinner
        Spinner countrySpinner = findViewById(R.id.countrySpinner);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(
                this, R.array.countries, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        // Next Button
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get selected language and country
                String selectedLanguage = getResources().getStringArray(R.array.language_codes)[languageSpinner.getSelectedItemPosition()];
                String selectedCountry = getResources().getStringArray(R.array.country_codes)[countrySpinner.getSelectedItemPosition()];

                // Start HomeActivity and pass the selected language and country
                startHomeActivity(selectedLanguage, selectedCountry);
            }
        });
    }

    private void startHomeActivity(String selectedLanguage, String selectedCountry) {
        // Start HomeActivity and pass the selected language and country
        Intent intent = new Intent(LanguageActivity.this, HomeActivity.class);
        intent.putExtra("SELECTED_LANGUAGE", selectedLanguage);
        intent.putExtra("SELECTED_COUNTRY", selectedCountry);
        startActivity(intent);
        finish();
    }
}
