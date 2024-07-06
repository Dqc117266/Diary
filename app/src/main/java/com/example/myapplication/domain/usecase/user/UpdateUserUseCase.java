package com.example.myapplication.domain.usecase.user;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserModel userModel, OnOperationCompleteListener listener) {
        try {
            userRepository.update(userModel);
            listener.onOperationComplete(true);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onOperationComplete(false);
        }
    }
}
