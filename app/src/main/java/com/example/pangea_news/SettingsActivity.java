package com.example.pangea_news;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton appearanceButton;
    private LinearLayout settingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        // Hide the default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Back button click listener
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the current activity and go back
            }
        });

        // Button with Appearance heading
        appearanceButton = findViewById(R.id.appearanceButton);
        appearanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                changeButtonColor();
                Toast.makeText(SettingsActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Checkbox 1
        CheckBox checkbox1 = findViewById(R.id.notificationCheckbox1);
        checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Handle checkbox 1 checked
                Toast.makeText(SettingsActivity.this, "Checkbox 1 checked", Toast.LENGTH_SHORT).show();
            } else {
                // Handle checkbox 1 unchecked
                Toast.makeText(SettingsActivity.this, "Checkbox 1 unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        // Checkbox 2
        CheckBox checkbox2 = findViewById(R.id.notificationCheckbox2);
        checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Handle checkbox 2 checked
                Toast.makeText(SettingsActivity.this, "Checkbox 2 checked", Toast.LENGTH_SHORT).show();
            } else {
                // Handle checkbox 2 unchecked
                Toast.makeText(SettingsActivity.this, "Checkbox 2 unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the layout for button color change
        settingsLayout = findViewById(R.id.settingsLayout);
    }

    // Method to change the button color
    private void changeButtonColor() {
        int[] colors = {ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.violet)};

        int currentColor = ((ColorDrawable) settingsLayout.getBackground()).getColor();
        int newColor = colors[(getColorIndex(currentColor) + 1) % colors.length];

        // Set the new color to the layout background
        settingsLayout.setBackgroundColor(newColor);
    }

    // Helper method to get the index of the current color in the colors array
    private int getColorIndex(int color) {
        int[] colors = {ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.violet)};

        for (int i = 0; i < colors.length; i++) {
            if (color == colors[i]) {
                return i;
            }
        }
        return -1; // Default index if not found
    }
}
