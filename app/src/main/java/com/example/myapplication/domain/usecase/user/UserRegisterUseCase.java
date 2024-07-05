package com.example.myapplication.domain.usecase.user;


import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;

public class UserRegisterUseCase {
    private final UserRepository userRepository;

    public UserRegisterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(UserModel userModel) {
        return userRepository.register(userModel);
    }
}
