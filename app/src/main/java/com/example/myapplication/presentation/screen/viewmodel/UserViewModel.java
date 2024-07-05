package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;
import com.example.myapplication.domain.usecase.user.DeleteUserUseCase;
import com.example.myapplication.domain.usecase.user.InsertUserUseCase;
import com.example.myapplication.domain.usecase.user.UserLoginUseCase;
import com.example.myapplication.domain.usecase.user.UserRegisterUseCase;

public class UserViewModel {
    private final UserRepository userRepository;
    private final UserLoginUseCase userLoginUseCase;
    private final UserRegisterUseCase userRegisterUseCase;
    private final InsertUserUseCase insertUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userLoginUseCase = new UserLoginUseCase(userRepository);
        this.userRegisterUseCase = new UserRegisterUseCase(userRepository);
        this.insertUserUseCase = new InsertUserUseCase(userRepository);
        this.deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    // 用户登录
    public UserModel login(String username, String password) {
        return userLoginUseCase.execute(username, password);
    }

    // 用户注册
    public boolean register(UserModel userModel) {
        return userRegisterUseCase.execute(userModel);
    }

    // 插入新用户
    public void insertUser(UserModel userModel, OnOperationCompleteListener listener) {
        insertUserUseCase.execute(userModel, listener);
    }

    public void deleteUser(UserModel userModel, OnOperationCompleteListener listener) {
        deleteUserUseCase.execute(userModel, listener);
    }
}
