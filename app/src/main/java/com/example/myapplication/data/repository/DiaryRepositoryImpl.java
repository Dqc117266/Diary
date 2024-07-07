package com.example.myapplication.data.repository;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.DiaryDao;
import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;

import java.util.List;
import java.util.concurrent.Future;

public class DiaryRepositoryImpl implements DiaryRepository {
    private final DiaryDao diaryDao;

    public DiaryRepositoryImpl(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    @Override
    public LiveData<List<DiaryModel>> getDiariesByUserId(int userId, String classify) {
        return diaryDao.getDiariesByUserId(userId, classify);
    }

    @Override
    public Future<?> updateDiary(DiaryModel diaryModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> diaryDao.updateDiary(diaryModel));
    }

    @Override
    public Future<?> deleteDiary(DiaryModel diaryModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> diaryDao.delete(diaryModel));
    }

    @Override
    public Future<?> insertDiary(DiaryModel diaryModel) {
        return AppDatabase.databaseWriteExecutor.submit(() -> diaryDao.insertDiary(diaryModel));
    }
}
