package com.instagram.downloader.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.instagram.downloader.R;
import com.instagram.downloader.databinding.ActivityShowStoriesBinding;

public class StoriesFullViewActivity extends AppCompatActivity {
    ActivityShowStoriesBinding binding;
    private StoriesFullViewActivity activity;
    private String filePathNew;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        binding = ActivityShowStoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_stories), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.activity = this;

        if (getIntent().getExtras() != null) {
            this.filePathNew = getIntent().getStringExtra(getString(R.string.imagedatafile));
            Glide.with(this.activity).load(this.filePathNew).into(this.binding.imageViewPreview);
        }
        initViews();
    }

    public void initViews() {
        this.binding.imageViewClose.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.activity = this;
    }
}