package com.example.myapplication.presentation.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.UserDao;
import com.example.myapplication.data.repository.UserRepositoryImpl;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.presentation.screen.viewmodel.RegisterViewModel;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        observeViewModel();
        initViews();
    }

    private void initViews() {

        binding.toLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.registerButton.setOnClickListener(view -> viewModel.register());
    }

    private void observeViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                UserDao userDao = AppDatabase.getDatabase(RegisterActivity.this).userDao();
                UserRepository userRepository = new UserRepositoryImpl(userDao);
                return (T) new RegisterViewModel(userRepository);
            }
        }).get(RegisterViewModel.class);


        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // 添加 TextWatcher 更新 ViewModel 数据
        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setRePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}