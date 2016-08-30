package net.gy.SwiftFrameWork.MVVM.Test;

import android.util.Log;


import net.gy.SwiftFrameWork.MVVM.Impl.JsonParse;
import net.gy.SwiftFrameWork.MVVM.ProxyHandler.DynamicHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pc on 16/8/29.
 */
public class Test {
    public void test(){
        Method[] methods = Test.class.getDeclaredMethods();

        DynamicHandler handler = new DynamicHandler(new Test());
        handler.addMethod("login", methods[0]);
        Object bussnessproxy = Proxy.newProxyInstance(ILogin.class.getClassLoader(), new Class<?>[]{ILogin.class}, handler);
        ILogin login = (ILogin) bussnessproxy;
        login.login("gy","login");
    }

    public void dosth(Object... pars){
        Log.e(pars[0].toString(),pars[1].toString());
    }

    public void getTree(){
        JsonParse.establish(TestPojo.class);
    }

}
