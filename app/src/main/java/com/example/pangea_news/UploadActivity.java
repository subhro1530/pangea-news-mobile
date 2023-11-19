package com.example.pangea_news;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UploadActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Enable the back button in the Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the LinearLayout for the gallery
        LinearLayout galleryContainer = findViewById(R.id.galleryContainer);

        // Check and request storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                // Permission already granted, proceed with fetching and displaying gallery images
                fetchAndDisplayGalleryImages();
            }
        } else {
            // For devices with SDK < 23, no runtime permission needed
            fetchAndDisplayGalleryImages();
        }

        // Set up the Camera button click listener
        ImageButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement camera functionality here
            }
        });
    }

    private void fetchAndDisplayGalleryImages() {
        // Fetch images from the user's gallery
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            // Display images in the gallery
            displayImage(absolutePathOfImage);
        }
        cursor.close();
    }

    private void displayImage(String imagePath) {
        ImageView imageView = new ImageView(this);
        imageView.setImageURI(Uri.parse(imagePath));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.gallery_image_size),
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.gallery_image_margin));
        imageView.setLayoutParams(layoutParams);

        // Add the image to the gallery container
        LinearLayout galleryContainer = findViewById(R.id.galleryContainer);
        galleryContainer.addView(imageView);
    }
}
