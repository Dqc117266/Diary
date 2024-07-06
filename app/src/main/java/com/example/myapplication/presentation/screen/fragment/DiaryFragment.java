package com.example.myapplication.presentation.screen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.data.database.model.UserModel;
import com.example.myapplication.databinding.FragmentDiaryBinding;
import com.example.myapplication.presentation.screen.adapter.DiaryPagerAdapter;
import com.example.myapplication.presentation.util.StringUtils;
import com.example.myapplication.presentation.util.UserPreferencesUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserPreferencesUtil util = UserPreferencesUtil.getInstance(getContext());
        if (util.isUserLoggedIn()) {
            UserModel currentUser = UserPreferencesUtil.getInstance(getContext()).getCurrentUser();
            List<String> tabNames =  StringUtils.parseTypesToList(currentUser.types);

            TabLayout tabLayout = binding.tabLayout;
            for (String tabName : tabNames) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(tabName);
                tabLayout.addTab(tab);
            }

            ViewPager2 viewPager2 = binding.viewPager;
            viewPager2.setAdapter(new DiaryPagerAdapter(requireActivity(), tabNames));

            // 将 ViewPager 和 TabLayout 关联起来
            new TabLayoutMediator(tabLayout, viewPager2,
                    (tab, position) -> tab.setText(tabNames.get(position))
            ).attach();

        }
    }
}
