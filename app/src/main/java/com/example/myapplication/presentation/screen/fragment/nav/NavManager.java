package com.example.myapplication.presentation.screen.fragment.nav;

import androidx.navigation.NavDirections;

public class NavManager {
    private NavEventListener navEventListener;

    public void navigate(NavDirections navDirections) {
        if (navEventListener != null) {
            navEventListener.onNavEvent(navDirections);
        }
    }

    public void setOnNavEvent(NavEventListener navEventListener) {
        this.navEventListener = navEventListener;
    }

    public interface NavEventListener {
        void onNavEvent(NavDirections navDirections);
    }
}
