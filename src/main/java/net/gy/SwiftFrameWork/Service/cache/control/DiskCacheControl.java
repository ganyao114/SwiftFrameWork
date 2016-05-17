package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.IDiskCache;

/**
 * Created by gy on 2015/11/5.
 */
public class DiskCacheControl implements IDiskCache {

    private IDiskCache cacheStrategy;

    @Override
    public boolean put(Object key, Object value) {
        cacheStrategy.put(key, value);
        return false;
    }

    @Override
    public Object get(Object key) {
        cacheStrategy.get(key);
        return null;
    }

    @Override
    public void clear() {

    }
}
