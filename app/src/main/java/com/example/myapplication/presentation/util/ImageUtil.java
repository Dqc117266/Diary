package com.example.myapplication.presentation.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtil {

    /**
     * 保存图片到本地存储
     * @param context 应用上下文
     * @param bitmap 要保存的Bitmap
     * @return 保存的图片路径
     */
    public static String saveImageToLocal(Context context, Bitmap bitmap) {
        File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(directory, fileName);
        try (OutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
    }
}

