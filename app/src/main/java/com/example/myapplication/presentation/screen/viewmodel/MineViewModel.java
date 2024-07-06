package com.example.myapplication.presentation.screen.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.presentation.util.UserPreferencesUtil;

public class MineViewModel extends AndroidViewModel {
    private final MutableLiveData<UserModel> currentUser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUserLoggedIn = new MutableLiveData<>();

    public MineViewModel(Application application) {
        super(application);
        UserPreferencesUtil userPreferencesUtil = UserPreferencesUtil.getInstance(application);
        UserModel userModel = userPreferencesUtil.getCurrentUser();
        if (userModel != null) {
            currentUser.setValue(userModel);
            isUserLoggedIn.setValue(true);
        } else {
            isUserLoggedIn.setValue(false);
        }
    }

    public LiveData<UserModel> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Boolean> getIsUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void logout() {
        UserPreferencesUtil.getInstance(getApplication()).clearCurrentUser();
        currentUser.setValue(null);
        isUserLoggedIn.setValue(false);
    }

    public void refreshUser() {
        UserPreferencesUtil userPreferencesUtil = UserPreferencesUtil.getInstance(getApplication());
        UserModel userModel = userPreferencesUtil.getCurrentUser();
        if (userModel != null) {
            currentUser.setValue(userModel);
            isUserLoggedIn.setValue(true);
        } else {
            isUserLoggedIn.setValue(false);
        }
    }
}

