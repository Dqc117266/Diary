package com.example.myapplication.domain.usecase.diary;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class DeleteDiaryUseCase {
    private final DiaryRepository diaryRepository;

    public DeleteDiaryUseCase(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void execute(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        try {
            diaryRepository.deleteDiary(diaryModel);
            listener.onOperationComplete(true);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onOperationComplete(false);
        }
    }
}

