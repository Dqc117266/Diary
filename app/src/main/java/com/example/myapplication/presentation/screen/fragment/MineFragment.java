package com.example.myapplication.presentation.screen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.databinding.FragmentMineBinding;
import com.example.myapplication.presentation.screen.activity.LoginActivity;
import com.example.myapplication.presentation.screen.activity.UserInfoActivity;
import com.example.myapplication.presentation.screen.viewmodel.MineViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private MineViewModel mineViewModel;
    private static final int LOGIN_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mineViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(MineViewModel.class);

        mineViewModel.getIsUserLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                binding.outLoginButton.setVisibility(View.VISIBLE);
                UserModel userModel = mineViewModel.getCurrentUser().getValue();
                if (userModel != null) {
                    binding.username.setText(userModel.username);
                }
            } else {
                binding.outLoginButton.setVisibility(View.GONE);
                binding.username.setText("登录/注册");
            }
        });

        binding.outLoginButton.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });

        binding.userItem.setOnClickListener(v -> {
            if (!mineViewModel.getIsUserLoggedIn().getValue()) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("确认退出")
                .setMessage("您确定要退出登录吗？")
                .setNegativeButton("取消", (dialog, which) -> {
                    // Do nothing
                })
                .setPositiveButton("确认", (dialog, which) -> {
                    mineViewModel.logout();
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mineViewModel.refreshUser();
    }
}
