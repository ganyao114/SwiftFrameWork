package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;

import java.util.Map;

/**
 * Created by pc on 16/8/9.
 */
public class CachePool<K,V> implements ICachePool<K,V>{

    private CachePoolGroup parent;
    private Map<K,V> cache;

    @Override
    public void init() {

    }

    @Override
    public void recycle() {

    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public void compress() {

    }

    @Override
    public ICachePool getParent() {
        return parent;
    }

    @Override
    public V findByKey(K key) {
        return null;
    }

    @Override
    public V findByRoute(String route) {
        return null;
    }

    @Override
    public V delById(K key) {
        return null;
    }

    @Override
    public V delByRoute(String route) {
        return null;
    }
}
