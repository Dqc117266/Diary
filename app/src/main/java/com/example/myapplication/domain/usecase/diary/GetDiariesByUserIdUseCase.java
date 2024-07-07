package com.example.myapplication.domain.usecase.diary;


import androidx.lifecycle.LiveData;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;

import java.util.List;

//获取特定用户的日记列表 Use Case
public class GetDiariesByUserIdUseCase {
    private final DiaryRepository diaryRepository;

    public GetDiariesByUserIdUseCase(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public LiveData<List<DiaryModel>> execute(int userId, String classify) {
        return diaryRepository.getDiariesByUserId(userId, classify);
    }
}
