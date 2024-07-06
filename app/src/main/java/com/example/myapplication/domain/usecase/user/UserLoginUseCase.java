package com.example.myapplication.domain.usecase.user;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;

public class UserLoginUseCase {
    private final UserRepository userRepository;

    public UserLoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel execute(String username, String password) {
        return userRepository.login(username, password);
    }
}
