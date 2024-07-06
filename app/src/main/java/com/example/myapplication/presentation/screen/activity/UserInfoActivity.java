package com.example.myapplication.presentation.screen.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.UserDao;
import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.data.repository.UserRepositoryImpl;
import com.example.myapplication.databinding.ActivityUserInfoBinding;
import com.example.myapplication.presentation.screen.viewmodel.UserInfoViewModel;
import com.example.myapplication.presentation.util.UserPreferencesUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class UserInfoActivity extends BaseActivity {

    private ActivityUserInfoBinding binding;
    private UserInfoViewModel userInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        userInfoViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                UserDao userDao = AppDatabase.getDatabase(UserInfoActivity.this).userDao();
                UserRepositoryImpl userRepository = new UserRepositoryImpl(userDao); // 实例化 UserRepositoryImpl
                return (T) new UserInfoViewModel(userRepository);
            }
        }).get(UserInfoViewModel.class);

        UserModel currentUser = UserPreferencesUtil.getInstance(this).getCurrentUser();
        userInfoViewModel.setCurrentUser(currentUser);
        Log.d("UserInfoActivity", " == " + currentUser.toString());

        // 监听用户信息变化
        userInfoViewModel.getCurrentUser().observe(this, userModel -> {
            binding.username.setText(userModel.nickname);
            binding.favorite.setText(userModel.favorite);
            binding.location.setText(userModel.locationStr);
            binding.birthday.setText(userModel.dataOfBirth);
            binding.self.setText(userModel.self);
        });

        // 为各个item添加点击事件
        binding.usernameLayout.setOnClickListener(v -> showEditDialog("昵称", currentUser.nickname, (newValue) -> {
            currentUser.nickname = newValue;
            userInfoViewModel.updateUser(currentUser, this::handleUpdateResult);
        }));

        binding.favoriteLayout.setOnClickListener(v -> showEditDialog("爱好", currentUser.favorite, (newValue) -> {
            currentUser.favorite = newValue;
            userInfoViewModel.updateUser(currentUser, this::handleUpdateResult);
        }));

        binding.locationLayout.setOnClickListener(v -> showEditDialog("所在地", currentUser.locationStr, (newValue) -> {
            currentUser.locationStr = newValue;
            userInfoViewModel.updateUser(currentUser, this::handleUpdateResult);
        }));

        binding.birthdayLayout.setOnClickListener(v -> showEditDialog("生日", currentUser.dataOfBirth, (newValue) -> {
            currentUser.dataOfBirth = newValue;
            userInfoViewModel.updateUser(currentUser, this::handleUpdateResult);
        }));

        binding.selfLayout.setOnClickListener(v -> showEditDialog("自我介绍", currentUser.self, (newValue) -> {
            currentUser.self = newValue;
            userInfoViewModel.updateUser(currentUser, this::handleUpdateResult);
        }));

    }

    private void showEditDialog(String title, String currentValue, OnValueChangeListener listener) {
        // 使用 MaterialAlertDialogBuilder 来创建 Material 3 对话框
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(title);

        // 使用 LayoutInflater 来加载自定义的视图
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText input = viewInflated.findViewById(R.id.input);
        input.setText(currentValue);

        // 将自定义视图设置到对话框中
        builder.setView(viewInflated);

        // 设置对话框的正按钮
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
            String newValue = input.getText().toString();
            listener.onValueChange(newValue);
        });

        // 设置对话框的负按钮
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        // 显示对话框
        builder.show();
    }

    private void handleUpdateResult(boolean success) {
        if (success) {
            UserModel updatedUser = userInfoViewModel.getCurrentUser().getValue();
            UserPreferencesUtil.getInstance(this).saveCurrentUser(updatedUser);
            userInfoViewModel.setCurrentUser(updatedUser);
            // 更新成功，可以做进一步的处理，比如显示提示信息
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            // 更新失败，处理错误
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    interface OnValueChangeListener {
        void onValueChange(String newValue);
    }
}