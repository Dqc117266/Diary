package com.example.myapplication.presentation.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class StatusBarUtil {

    /**
     * 获取状态栏的高度
     * @param context 上下文
     * @return 状态栏高度（以像素为单位）
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取底部导航栏的高度
     * @param context 上下文
     * @return 底部导航栏高度（以像素为单位）
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 将 dp 转换为像素
     * @param context 上下文
     * @param dp dp 值
     * @return 像素值
     */
    public static int dpToPx(Context context, int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    public static int getScreenWidth(Context context) {
        // 获取屏幕宽度
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 将像素转换为 dp
     * @param context 上下文
     * @param px 像素值
     * @return dp 值
     */
    public static int pxToDp(Context context, int px) {
        return Math.round(px / context.getResources().getDisplayMetrics().density);
    }
}

