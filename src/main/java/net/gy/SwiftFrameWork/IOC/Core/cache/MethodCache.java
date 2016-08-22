package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 16/8/21.
 */
public class MethodCache implements ICacheEntry<Class,MethodEntity[]> {

    private Map<Class,MethodEntity[]> cache;

    public MethodCache() {
        cache = new ConcurrentHashMap<Class, MethodEntity[]>();
    }

    @Override
    public boolean put(Class key, MethodEntity[] value) {
        if (cache.containsKey(key))
            return false;
        else
            cache.put(key, value);
        return true;
    }

    @Override
    public MethodEntity[] get(Class key) {
        return cache.get(key);
    }

    @Override
    public MethodEntity[] remove(Class aClass) {
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
}
