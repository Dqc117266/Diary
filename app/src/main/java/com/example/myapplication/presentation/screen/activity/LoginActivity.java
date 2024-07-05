package com.example.myapplication.presentation.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.UserDao;
import com.example.myapplication.data.repository.UserRepositoryImpl;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.presentation.screen.viewmodel.LoginViewModel;
import com.example.myapplication.presentation.util.UserPreferencesUtil;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                UserDao userDao = AppDatabase.getDatabase(LoginActivity.this).userDao();
                UserRepositoryImpl userRepository = new UserRepositoryImpl(userDao); // 实例化 UserRepositoryImpl
                return (T) new LoginViewModel(userRepository);
            }
        }).get(LoginViewModel.class);

        initViews();
        observeViewModel();
    }

    private void initViews() {
        binding.toRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.loginButton.setOnClickListener(view -> viewModel.login());

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
    }

    private void observeViewModel() {
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getLoginUser().observe(this, user -> {
            if (user != null) {
                UserPreferencesUtil.getInstance(LoginActivity.this).saveCurrentUser(user);
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}