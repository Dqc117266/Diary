package com.example.myapplication.presentation.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> parseTypesToList(String types) {
        List<String> typesList = new ArrayList<>();

        if (types != null && !types.isEmpty()) {
            String[] typesArray = types.split(",");
            for (String type : typesArray) {
                typesList.add(type.trim());
            }
        }

        return typesList;
    }
}
