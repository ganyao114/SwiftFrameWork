package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpMethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by pc on 16/8/30.
 */
public class MvvmCache {

    private static Map<Class,MvvmCache> cacheMap = new ConcurrentHashMap<Class, MvvmCache>();
    private static Map<Class,Method> proxymethod = new ConcurrentHashMap<>();
    private Map<Method,MethodCache> methodCaches;

    public static Method getProxyMethod(Class clazz){
        Method method = proxymethod.get(clazz);
        if (method == null){
            try {
                method = clazz.getDeclaredMethod(HttpMethodProxy.METHOD_RET,Method.class,Object[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            proxymethod.put(clazz,method);
        }
        return method;
    }

    public static Map<Class, MvvmCache> getCacheMap() {
        return cacheMap;
    }

    public Map<Method,MethodCache>  getMethodCaches() {
        return methodCaches;
    }

    public void setMethodCaches(Map<Method,MethodCache>  methodCaches) {
        this.methodCaches = methodCaches;
    }
}
