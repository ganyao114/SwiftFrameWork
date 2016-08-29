package net.gy.SwiftFrameWork.MVVM.Test;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/29.
 */
public class Test {
    public void test(){
        Method[] methods = ILogin.class.getDeclaredMethods();
        Log.e("gy",methods[0].getName());
    }
}
