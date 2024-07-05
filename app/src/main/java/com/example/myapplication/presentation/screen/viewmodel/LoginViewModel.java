package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.user.UserLoginUseCase;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final UserLoginUseCase userLoginUseCase;

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<UserModel> loginUser = new MutableLiveData<>();

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userLoginUseCase = new UserLoginUseCase(userRepository);
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

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<UserModel> getLoginUser() {
        return loginUser;
    }

    public void login() {
        String usernameValue = username.getValue();
        String passwordValue = password.getValue();

        if (usernameValue == null || usernameValue.isEmpty()) {
            errorMessage.setValue("请输入用户名");
            return;
        }

        if (passwordValue == null || passwordValue.isEmpty()) {
            errorMessage.setValue("请输入密码");
            return;
        }

        AppDatabase.databaseWriteExecutor.submit(() -> {
            UserModel user = userLoginUseCase.execute(usernameValue, passwordValue);
            if (user == null) {
                errorMessage.postValue("用户名或密码错误");
            } else {
                loginUser.postValue(user);
                errorMessage.postValue(null);
            }
        });
    }
}

