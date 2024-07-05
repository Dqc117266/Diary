package com.example.myapplication.data.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Users")
public class UserModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String password;
    public String avatarPath; //头像地址
    public String locationStr; //所在地
    public String dataOfBirth;//出生日期
    public String self; //自我介绍
    public String favorite; //爱好
    public String types; //类型
}
