package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.diary.DeleteDiaryUseCase;
import com.example.myapplication.domain.usecase.diary.GetDiariesByUserIdUseCase;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

import java.util.List;

public class DiaryContentViewModel extends ViewModel {
    private final GetDiariesByUserIdUseCase getDiariesByUserIdUseCase;
    private final DeleteDiaryUseCase deleteDiaryUseCase;
    private final LiveData<List<DiaryModel>> diariesLiveData;

    public DiaryContentViewModel(DiaryRepository diaryRepository, int userId, String classify) {
        this.getDiariesByUserIdUseCase = new GetDiariesByUserIdUseCase(diaryRepository);
        this.deleteDiaryUseCase = new DeleteDiaryUseCase(diaryRepository);

        diariesLiveData = getDiariesByUserIdUseCase.execute(userId, classify);
    }

    public LiveData<List<DiaryModel>> getDiariesLiveData() {
        return diariesLiveData;
    }

    public void getDiariesByUserId(int userId, String classify) {
        getDiariesByUserIdUseCase.execute(userId, classify);
    }

    public void deleteDiary(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        deleteDiaryUseCase.execute(diaryModel, listener);
    }

}
