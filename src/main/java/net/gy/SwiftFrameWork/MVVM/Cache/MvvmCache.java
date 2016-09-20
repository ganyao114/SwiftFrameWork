package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpMethodProxy;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存实体
 * Created by pc on 16/8/30.
 */
public class MvvmCache {

    private static Map<Class,MvvmCache> cacheMap = new ConcurrentHashMap<Class, MvvmCache>();
    private static Map<Class,Method> proxymethod = new ConcurrentHashMap<>();
    private static Map<Class<? extends IFilter>,IFilter> filterMap = new ConcurrentHashMap<Class<? extends IFilter>, IFilter>();
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

    public static IFilter getFilter(Class<? extends IFilter> type){
        IFilter filter = filterMap.get(type);
        if (filter == null){
            try {
                filter = type.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            filterMap.put(type,filter);
        }
        return filter;
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
