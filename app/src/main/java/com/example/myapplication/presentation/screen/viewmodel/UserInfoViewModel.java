package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;
import com.example.myapplication.domain.usecase.user.UpdateUserUseCase;

public class UserInfoViewModel extends ViewModel {
    private final UpdateUserUseCase updateUserUseCase;
    private final MutableLiveData<UserModel> currentUser;

    public UserInfoViewModel(UserRepository userRepository) {
        updateUserUseCase = new UpdateUserUseCase(userRepository);
        this.currentUser = new MutableLiveData<>();
    }

    public LiveData<UserModel> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserModel userModel) {
        currentUser.setValue(userModel);
    }

    public void updateUser(UserModel userModel, OnOperationCompleteListener listener) {
        updateUserUseCase.execute(userModel, listener);
    }
}
