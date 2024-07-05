package com.example.myapplication.presentation.util;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

public class NavigationUtil {
    public static void navigateSafely(NavController navController, NavDirections navDirections) {
        if (navController.getCurrentDestination().getAction(navDirections.getActionId()) != null) {
            navController.navigate(navDirections);
        }
    }
}
