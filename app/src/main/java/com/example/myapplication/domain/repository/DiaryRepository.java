package com.example.myapplication.domain.repository;


import androidx.lifecycle.LiveData;

import com.example.myapplication.data.database.model.DiaryModel;

import java.util.List;
import java.util.concurrent.Future;

public interface DiaryRepository {
    LiveData<List<DiaryModel>> getDiariesByUserId(int userId);

    Future<?> updateDiary(DiaryModel diaryModel);

    Future<?> deleteDiary(DiaryModel diaryModel);

    Future<?> insertDiary(DiaryModel diaryModel);
}
