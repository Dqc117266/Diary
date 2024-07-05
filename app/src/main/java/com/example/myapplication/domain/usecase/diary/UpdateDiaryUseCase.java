package com.example.myapplication.domain.usecase.diary;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class UpdateDiaryUseCase {
    private final DiaryRepository diaryRepository;

    public UpdateDiaryUseCase(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void execute(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        try {
            diaryRepository.updateDiary(diaryModel);
            listener.onOperationComplete(true);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onOperationComplete(false);
        }
    }
}
