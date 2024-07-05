package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.diary.DeleteDiaryUseCase;
import com.example.myapplication.domain.usecase.diary.GetDiariesByUserIdUseCase;
import com.example.myapplication.domain.usecase.diary.InsertDiaryUseCase;
import com.example.myapplication.domain.usecase.diary.UpdateDiaryUseCase;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

import java.util.List;

public class DiaryViewModel extends ViewModel {
    private final DiaryRepository diaryRepository;
    private final GetDiariesByUserIdUseCase getDiariesByUserIdUseCase;
    private final InsertDiaryUseCase insertDiaryUseCase;
    private final UpdateDiaryUseCase updateDiaryUseCase;
    private final DeleteDiaryUseCase deleteDiaryUseCase;

    public DiaryViewModel(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
        this.getDiariesByUserIdUseCase = new GetDiariesByUserIdUseCase(diaryRepository);
        this.insertDiaryUseCase = new InsertDiaryUseCase(diaryRepository);
        this.updateDiaryUseCase = new UpdateDiaryUseCase(diaryRepository);
        this.deleteDiaryUseCase = new DeleteDiaryUseCase(diaryRepository);
    }

    // 获取特定用户的日记列表
    public LiveData<List<DiaryModel>> getDiariesByUserId(int userId) {
        return getDiariesByUserIdUseCase.execute(userId);
    }

    // 插入新日记
    public void insertDiary(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        insertDiaryUseCase.execute(diaryModel, listener);
    }

    // 更新日记内容
    public void updateDiary(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        updateDiaryUseCase.execute(diaryModel, listener);
    }

    // 删除日记
    public void deleteDiary(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        deleteDiaryUseCase.execute(diaryModel, listener);
    }

}
