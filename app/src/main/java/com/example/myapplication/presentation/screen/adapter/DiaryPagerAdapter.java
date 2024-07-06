package com.example.myapplication.presentation.screen.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.presentation.screen.fragment.DiaryContentFragment;

import java.util.List;

public class DiaryPagerAdapter extends FragmentStateAdapter {

    private final List<String> tabNames;

    public DiaryPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> tabNames) {
        super(fragmentActivity);
        this.tabNames = tabNames;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置创建不同的 Fragment 来显示不同的内容
        return DiaryContentFragment.newInstance(tabNames.get(position));
    }

    @Override
    public int getItemCount() {
        return tabNames.size();
    }
}

