package com.rjwl.reginet.teacherread.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 * 数据保存
 */

public class SaveOrDeletePrefrence {

    public static void save(Context context, String key, String value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);

        editor.commit();

    }

    public static void saveBoolean(Context context, String key, boolean b) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key, b);

        editor.commit();

    }

    public static boolean lookBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean(key, false);

        return b;
    }

    public static void saveList(Context context, String key, List<String> list) {
        saveInt(context, key + "size", list.size());
        for (int i = 0; i < list.size(); i++) {
            save(context, key + i, list.get(i));
        }
    }

    public static List<String> getList(Context context, String key) {
        List<String> list = new ArrayList<>();
        int size = lookInt(context, key + "size");
        String value = "";
        for(int i = 0; i < size; i++) {
            value = look(context, key + i);
            if ("".equals(value)) {
                continue;
            }
            list.add(value);
        }

        return list;
    }

    public static void delete(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, "");
        editor.commit();
    }

    public static String look(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        String data = sharedPreferences.getString(key, "");

        return data;
    }

    public static void deleteAll(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Read", Activity.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static int lookInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Read",
                Activity.MODE_PRIVATE);
        int data = sharedPreferences.getInt(key, 0);
        return data;
    }

}
