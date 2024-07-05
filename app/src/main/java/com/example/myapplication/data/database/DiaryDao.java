package com.example.myapplication.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.database.model.DiaryModel;

import java.util.List;

@Dao
public interface DiaryDao {

    @Query("SELECT * FROM DiaryList WHERE userId = :userId")
    LiveData<List<DiaryModel>> getDiariesByUserId(int userId);

    @Update
    void updateDiary(DiaryModel diaryModel);

    @Delete
    void delete(DiaryModel diaryModel);

    @Insert
    void insertDiary(DiaryModel diaryModel);

}
