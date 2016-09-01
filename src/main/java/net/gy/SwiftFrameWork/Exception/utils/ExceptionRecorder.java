package net.gy.SwiftFrameWork.Exception.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;

/**
 * Created by gy on 2016/4/2.
 */
public class ExceptionRecorder {
    private final static String filename = "Exceptions";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences getSharedPreferences(){
        if (sharedPreferences == null){
            sharedPreferences = IOC.getInstance()
                    .getApplication()
                    .getApplicationContext()
                    .getSharedPreferences(filename, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
    public static void recordrException(String time,String content){
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(time,content);
        editor.commit();
    }
}
