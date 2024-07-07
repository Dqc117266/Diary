package com.example.myapplication.presentation.screen.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.databinding.ActivityDiaryDetailBinding;

public class DiaryDetailActivity extends BaseActivity {

    private ActivityDiaryDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDiaryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("diaryModel")) {
            DiaryModel diaryModel = (DiaryModel) getIntent().getSerializableExtra("diaryModel");
            if (diaryModel != null) {
                binding.toolbar.setTitle(diaryModel.diaryName);
                binding.dateTextView.setText(diaryModel.date);
                binding.contentTextView.setText(diaryModel.diaryContent);
                binding.typeTextView.setText(diaryModel.classify);

                if (diaryModel.imagePath != null && !diaryModel.imagePath.isEmpty()) {
                    Glide.with(this).load(diaryModel.imagePath).into(binding.image);
                }
            }
        }
    }
}