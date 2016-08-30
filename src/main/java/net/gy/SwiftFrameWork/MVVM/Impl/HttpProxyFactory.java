package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpMethodProxy;
import net.gy.SwiftFrameWork.MVVM.ProxyHandler.DynamicHandler;
import net.gy.SwiftFrameWork.MVVM.Test.ILogin;

import java.lang.reflect.Proxy;

/**
 * Created by pc on 16/8/30.
 */
public class HttpProxyFactory {
    public static <T> T  establish(Class<?> inf){

        HttpMethodProxy proxy = new HttpMethodProxy(null,null);
        DynamicHandler handler = new DynamicHandler(proxy);
//        handler.addMethod("login", methods[0]);
        T bussnessproxy = (T) Proxy.newProxyInstance(ILogin.class.getClassLoader(), new Class<?>[]{ILogin.class}, handler);
        return bussnessproxy;
    }
}
