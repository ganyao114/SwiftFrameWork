package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Cache.MethodCache;
import net.gy.SwiftFrameWork.MVVM.Cache.MvvmCache;
import net.gy.SwiftFrameWork.MVVM.Cache.MvvmCacheControl;
import net.gy.SwiftFrameWork.MVVM.Entity.HttpMethodProxy;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.ProxyHandler.DynamicHandler;
import net.gy.SwiftFrameWork.MVVM.Test.ILogin;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class HttpProxyFactory {

    private static HttpProxyFactory factory;

    public static HttpProxyFactory getInstance(){
        if (factory == null){
            synchronized (HttpProxyFactory.class){
                if (factory == null)
                    factory = new HttpProxyFactory();
            }
        }
        return factory;
    }

    public static ExtCall With(Class inf){
        return new ExtCall(inf);
    }

    public <T> T establish(Class<?> inf,ICallBack callBack,Map<String,ICallBack> callBacks){

        MvvmCache cache = MvvmCacheControl.getCache(inf);

        for (MethodCache methodCache:cache.getMethodCaches()){

        }


        HttpMethodProxy proxy = new HttpMethodProxy();
        DynamicHandler handler = new DynamicHandler(proxy);
//        handler.addMethod("login", methods[0]);
        T bussnessproxy = (T) Proxy.newProxyInstance(inf.getClassLoader(), new Class<?>[]{inf}, handler);
        return bussnessproxy;
    }

    static class ExtCall{

        private Class inf;

        private ICallBack callBack;

        private Map<String,ICallBack> callBacks;

        public ExtCall(Class inf) {
            this.inf = inf;
        }

        public ExtCall addCallBack(String methodName,ICallBack callBack){
            if (callBacks == null)
                callBacks = new HashMap<>();
            callBacks.put(methodName,
                    callBack);
            return this;
        }

        public ExtCall setCallBack(ICallBack callBack){
            callBacks = null;
            this.callBack = callBack;
            return this;
        }

        public void establish(){
            getInstance().establish(inf,callBack,callBacks);
        }

    }

}
