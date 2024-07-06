package com.example.myapplication.presentation.screen.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presentation.screen.fragment.nav.NavManager;
import com.example.myapplication.presentation.util.NavigationUtil;
import com.example.myapplication.presentation.util.StatusBarUtil;
import com.example.myapplication.presentation.util.UserPreferencesUtil;

public class MainActivity extends BaseActivity implements NavController.OnDestinationChangedListener {

    private ActivityMainBinding binding;
    private NavManager navManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initNavManager();
        initBottomNavigation();
    }

    private void initViews() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) binding.fabAdd.getLayoutParams();
        layoutParams.bottomMargin = StatusBarUtil.getNavigationBarHeight(this) + StatusBarUtil.dpToPx(this, 48);
        binding.fabAdd.setLayoutParams(layoutParams);

        binding.fabAdd.setOnClickListener(view -> {
            if (!UserPreferencesUtil.getInstance(this).isUserLoggedIn()) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, AddDiaryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initNavManager() {
        navManager = new NavManager();

        navManager.setOnNavEvent(it -> {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
            assert navHostFragment != null;
            assert navHostFragment.getChildFragmentManager().getFragments().size() > 0;

            NavController navController = navHostFragment.getNavController();
            NavigationUtil.navigateSafely(navController, it);
        });
    }

    private void initBottomNavigation() {
        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment)).getNavController();
        navController.addOnDestinationChangedListener(this);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {

    }
}