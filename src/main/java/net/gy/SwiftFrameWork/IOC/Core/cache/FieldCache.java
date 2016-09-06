package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 16/8/21.
 */
public class FieldCache implements ICacheEntry<Class,FieldEntity[]> {

    private Map<Class,FieldEntity[]> cache;

    public FieldCache() {
        cache = new ConcurrentHashMap<Class, FieldEntity[]>();
    }

    @Override
    public boolean put(Class key, FieldEntity[] value) {
        if (cache.containsKey(key))
            return false;
        else
            cache.put(key,value);
        return true;
    }

    @Override
    public FieldEntity[] get(Class key) {
        return cache.get(key);
    }

    @Override
    public Set<Class> getKeys() {
        return null;
    }

    @Override
    public FieldEntity[] remove(Class aClass) {
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
    public ICacheEntry<Class, FieldEntity[]> getEntry() {
        return null;
    }
}
