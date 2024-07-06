package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.user.UserRegisterUseCase;

public class RegisterViewModel extends ViewModel {
    private final UserRegisterUseCase userRegisterUseCase;

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> rePassword = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public RegisterViewModel(UserRepository userRepository) {
        this.userRegisterUseCase = new UserRegisterUseCase(userRepository);
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<String> getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword.setValue(rePassword);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void register() {
        String usernameValue = username.getValue();
        String passwordValue = password.getValue();
        String rePasswordValue = rePassword.getValue();

        if (usernameValue == null || usernameValue.isEmpty()) {
            errorMessage.setValue("请输入用户名");
            return;
        }

        if (passwordValue == null || passwordValue.isEmpty()) {
            errorMessage.setValue("请输入密码");
            return;
        }

        if (rePasswordValue == null || rePasswordValue.isEmpty()) {
            errorMessage.setValue("请确认密码");
            return;
        }

        if (!passwordValue.equals(rePasswordValue)) {
            errorMessage.setValue("两次输入的密码不一致");
            return;
        }

        UserModel userModel = new UserModel();
        userModel.username = usernameValue;
        userModel.password = passwordValue;
        userModel.types = "我的日记,学习笔记,日常生活";

        AppDatabase.databaseWriteExecutor.submit(()-> {
            boolean success = userRegisterUseCase.execute(userModel);
            if (!success) {
                errorMessage.postValue("该账户已注册");
            } else {
                errorMessage.postValue(null);
            }
        });
    }
}

