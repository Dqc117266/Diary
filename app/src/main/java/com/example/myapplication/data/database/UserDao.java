package com.example.myapplication.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myapplication.data.database.model.UserModel;

@Dao
public interface UserDao {

    @Insert
    void insert(UserModel userModel);

    @Delete
    void delete(UserModel userModel);

    @Query("SELECT * FROM users WHERE username = :username and password = :password")
    UserModel login(String username, String password);

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    int checkUsernameExists(String username);

    @Transaction
    default boolean register(UserModel userModel) {
        if (checkUsernameExists(userModel.username) > 0) {
            return false; // 用户名已存在，注册失败
        } else {
            insert(userModel); // 插入新用户
            return true; // 注册成功
        }
    }
}
