package com.example.myapplication.presentation.screen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.DiaryDao;
import com.example.myapplication.data.repository.DiaryRepositoryImpl;
import com.example.myapplication.databinding.FragmentDiaryContentBinding;
import com.example.myapplication.domain.repository.DiaryRepository;
import com.example.myapplication.presentation.screen.adapter.DiaryListAdapter;
import com.example.myapplication.presentation.screen.viewmodel.DiaryContentViewModel;
import com.example.myapplication.presentation.util.UserPreferencesUtil;

public class DiaryContentFragment extends Fragment {

    private static final String ARG_TAB_NAME = "tabName";
    private FragmentDiaryContentBinding binding;
    private DiaryListAdapter adapter;
    private DiaryContentViewModel viewModel;

    public static DiaryContentFragment newInstance(String tabName) {
        DiaryContentFragment fragment = new DiaryContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_NAME, tabName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDiaryContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                DiaryDao diaryDao = AppDatabase.getDatabase(getContext()).diaryDao();
                DiaryRepository diaryRepository = new DiaryRepositoryImpl(diaryDao); // 假设您有一个 DiaryRepository 实例
                int userId = UserPreferencesUtil.getInstance(getContext()).getCurrentUser().id;
                String classify = getArguments().getString(ARG_TAB_NAME); // 使用 tabName 作为 classify

                return (T) new DiaryContentViewModel(diaryRepository, userId, classify);
            }
        }).get(DiaryContentViewModel.class);


        adapter = new DiaryListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDiariesLiveData().observe(getViewLifecycleOwner(), diaries -> {
            adapter.submitList(diaries);
        });
    }
}
