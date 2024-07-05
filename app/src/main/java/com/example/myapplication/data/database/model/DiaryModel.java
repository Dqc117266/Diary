package com.example.myapplication.data.database.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "DiaryList",
        foreignKeys = @ForeignKey(
                entity = UserModel.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("userId")}
)
public class DiaryModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String imagePath;
    public String diaryName;
    public String diaryContent;
    public String classify;
    public String date;
    public int userId; // 外键，关联用户id
}
