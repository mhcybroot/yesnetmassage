package com.ysenetdigital.yesnetmassage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.databinding.ActivityImageViewerBinding;

public class ImageViewer extends AppCompatActivity {
ActivityImageViewerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String url = getIntent().getStringExtra("url");
        if (url != null){
            Picasso.get().load(url).placeholder(R.drawable.ic_baseline_image_24).into(binding.imageViewerImage);

        }

    }
}