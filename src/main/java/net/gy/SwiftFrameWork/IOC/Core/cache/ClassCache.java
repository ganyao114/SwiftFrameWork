package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 16/8/21.
 */
public class ClassCache implements ICacheEntry<Class,Map<Class<? extends Annotation>,Annotation>> {

    private Map<Class,Map<Class<? extends Annotation>,Annotation>> cache;

    public ClassCache() {
        cache = new ConcurrentHashMap<Class, Map<Class<? extends Annotation>,Annotation>>();
    }

    @Override
    public boolean put(Class key, Map<Class<? extends Annotation>,Annotation> value) {
        if (cache.containsKey(key))
            return false;
        else
            cache.put(key,value);
        return true;
    }

    @Override
    public Map<Class<? extends Annotation>,Annotation> get(Class key) {
        return cache.get(key);
    }

    @Override
    public Set<Class> getKeys() {
        return null;
    }

    @Override
    public Map<Class<? extends Annotation>,Annotation> remove(Class aClass) {
        return cache.remove(aClass);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void setlimit(long size) {

    }

    @Override
    public long compress() {
        return 0;
    }

    @Override
    public void swapout() {

    }

    @Override
    public void swapin() {

    }

    @Override
    public ICacheEntry<Class, Map<Class<? extends Annotation>, Annotation>> getEntry() {
        return this;
    }
}
