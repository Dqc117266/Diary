package com.example.myapplication.domain.repository;

import com.example.myapplication.data.database.model.UserModel;

import java.util.concurrent.Future;

public interface UserRepository {
    Future<?> insert(UserModel userModel);

    Future<?> update(UserModel userModel);

    Future<?> delete(UserModel userModel);

    UserModel login(String username, String password);

    boolean register(UserModel userModel);
}
