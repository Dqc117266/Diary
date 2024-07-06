package com.example.myapplication.data.repository;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.UserDao;
import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.domain.repository.UserRepository;

import java.util.concurrent.Future;

public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Future<?> insert(UserModel userModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> userDao.insert(userModel));
    }

    @Override
    public Future<?> update(UserModel userModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> userDao.update(userModel)); // 添加更新方法
    }

    @Override
    public Future<?> delete(UserModel userModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> userDao.delete(userModel));
    }

    @Override
    public UserModel login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public boolean register(UserModel userModel) {
        return userDao.register(userModel);
    }
}
