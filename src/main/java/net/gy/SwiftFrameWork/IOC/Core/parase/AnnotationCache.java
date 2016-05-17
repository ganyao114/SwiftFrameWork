package net.gy.SwiftFrameWork.IOC.Core.parase;

import net.gy.SwiftFrameWork.Service.cache.IRamCache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2016/3/8.
 */
public final class AnnotationCache implements IRamCache<Class,List<Method>>{

    private Map<Class,List<Method>> caches;

    public AnnotationCache(){
        caches = new HashMap<Class,List<Method>>();
    }

    @Override
    public boolean put(Class key, List<Method> value) {
        caches.put(key,value);
        return true;
    }

    @Override
    public List<Method> get(Class key) {
        List<Method> methods = caches.get(key);
        if (methods == null) {
            methods = doParase(key);
            put(key,methods);
        }
        return methods;
    }

    private List<Method> doParase(Class key) {
       return Arrays.asList(key.getDeclaredMethods());
    }

    @Override
    public void clear() {
        caches.clear();
    }

    @Override
    public void setlimit(long size) {

    }

    public void remove(Class clazz){
        if (caches.containsKey(clazz))
            caches.remove(clazz);
    }
}
