package com.example.myapplication.domain.usecase.user;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class InsertUserUseCase {
    private final UserRepository userRepository;

    public InsertUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserModel userModel, OnOperationCompleteListener listener) {

        try {
            userRepository.insert(userModel);
            listener.onOperationComplete(true);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onOperationComplete(false);
        }
    }
}
