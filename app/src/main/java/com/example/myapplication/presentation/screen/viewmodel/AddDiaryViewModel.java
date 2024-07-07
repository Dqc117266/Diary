package com.example.myapplication.presentation.screen.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.domain.usecase.diary.InsertDiaryUseCase;
import com.example.myapplication.domain.usecase.diary.UpdateDiaryUseCase;
import com.example.myapplication.domain.usecase.listener.OnOperationCompleteListener;

public class AddDiaryViewModel extends ViewModel {
    private final InsertDiaryUseCase insertDiaryUseCase;
    private final UpdateDiaryUseCase updateDiaryUseCase;
    private MutableLiveData<DiaryModel> diaryModel = new MutableLiveData<>();

    public AddDiaryViewModel(DiaryRepository repository) {
        insertDiaryUseCase = new InsertDiaryUseCase(repository);
        updateDiaryUseCase = new UpdateDiaryUseCase(repository);
    }

    public LiveData<DiaryModel> getDiaryModel() {
        return diaryModel;
    }

    public void setDiaryModel(MutableLiveData<DiaryModel> diaryModel) {
        this.diaryModel = diaryModel;
    }

    public void saveDiary(DiaryModel diaryModel, OnOperationCompleteListener listener) {
        insertDiaryUseCase.execute(diaryModel, listener);
    }

}
