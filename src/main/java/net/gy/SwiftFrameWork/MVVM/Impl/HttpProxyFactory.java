package net.gy.SwiftFrameWork.MVVM.Impl;

import android.app.Activity;
import android.view.View;

import net.gy.SwiftFrameWork.MVVM.Cache.MethodCache;
import net.gy.SwiftFrameWork.MVVM.Cache.MvvmCache;
import net.gy.SwiftFrameWork.MVVM.Cache.MvvmCacheControl;
import net.gy.SwiftFrameWork.MVVM.Entity.HttpMethodProxy;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.ProxyHandler.DynamicHandler;
import net.gy.SwiftFrameWork.MVVM.Test.ILogin;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
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

    public <T> T establish(Class<?> inf,Map<String,ICallBack> callBacks,Map<String,WeakReference<View>> viewContentRef){

        MvvmCache cache = MvvmCacheControl.getCache(inf);

        Method proxymethod = MvvmCache.getProxyMethod(HttpMethodProxy.class);

        if (cache == null)
            return null;

        HttpMethodProxy proxy = new HttpMethodProxy(cache.getMethodCaches(),callBacks);

        proxy.setViewContentRef(viewContentRef);

        DynamicHandler handler = new DynamicHandler(proxy);
        for (Map.Entry<Method,MethodCache> methodCache:cache.getMethodCaches().entrySet()){
            handler.addMethod(methodCache.getKey().getName(), proxymethod);
        }
//        handler.addMethod("login", methods[0]);
        T bussnessproxy = (T) Proxy.newProxyInstance(inf.getClassLoader(), new Class<?>[]{inf}, handler);
        return bussnessproxy;
    }

    public static class ExtCall{

        private Class inf;

        private Map<String,ICallBack> callBacks =  new HashMap<>();

        private Map<String,WeakReference<View>> viewContentRef = new HashMap<>();

        public ExtCall(Class inf) {
            this.inf = inf;
        }

        public ExtCall addCallBack(String methodName,ICallBack callBack){
            callBacks.put(methodName,
                    callBack);
            return this;
        }

        public ExtCall addViewContent(String methodName,View view){
            viewContentRef.put(methodName,new WeakReference<View>(view));
            return this;
        }

        public ExtCall addViewContent(String methodName,Activity activity){
            View view = activity.getWindow().getDecorView();
            return addViewContent(methodName, view);
        }

        public <T> T establish(){
            return (T) getInstance().establish(inf,callBacks,viewContentRef);
        }

    }

}
