package com.example.myapplication.presentation.screen.activity;

import android.os.Bundle;
import com.example.myapplication.databinding.ActivityAddDiaryBinding;

public class AddDiaryActivity extends BaseActivity {

    private ActivityAddDiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityAddDiaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }
}