package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.entity.ICache;

import java.util.Map;

/**
 * Created by pc on 16/8/9.
 */
public class CachePool<K,V> implements ICachePool<K,V>{

    public volatile static ICachePool poolRoot;

    private CachePoolGroup parent;

    private ICache<K,V> cache;

    private boolean compressable = false;

    public static ICachePool buildPool(){
        if (poolRoot == null){
            synchronized (CachePool.class){
                if (poolRoot == null){
                    establish();
                }
            }
        }
        return poolRoot;
    }

    private static void establish() {

    }

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

    @Override
    public V refreshByKey(K key) {
        return null;
    }

    @Override
    public V refreshByRoute(String route) {
        return null;
    }

    @Override
    public boolean compressable() {
        return compressable;
    }
}
