package com.example.myapplication.domain.usecase.diary;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class InsertDiaryUseCase {
    private final DiaryRepository diaryRepository;

    public InsertDiaryUseCase(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void execute(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        try {
            diaryRepository.insertDiary(diaryModel);
            listener.onOperationComplete(true);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onOperationComplete(false);
        }
    }
}
