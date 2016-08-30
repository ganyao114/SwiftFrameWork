package net.gy.SwiftFrameWork.MVVM.Cache;

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
    private List<MethodCache> methodCaches;

    public static Map<Class, MvvmCache> getCacheMap() {
        return cacheMap;
    }

    public List<MethodCache> getMethodCaches() {
        return methodCaches;
    }

    public void setMethodCaches(List<MethodCache> methodCaches) {
        this.methodCaches = methodCaches;
    }
}
