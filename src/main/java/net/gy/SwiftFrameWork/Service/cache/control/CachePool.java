package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.IRamCache;
import net.gy.SwiftFrameWork.Service.cache.entity.ICache;
import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by pc on 16/8/9.
 */
public class CachePool<K,V> implements ICachePool<K,V>{

    public volatile static ICachePool poolRoot;

    private CachePoolGroup parent;

    private ICacheEntry<K,V> cache;

    protected int level;

    private boolean compressable = false;

    public AtomicBoolean isMtc = new AtomicBoolean(false);

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
        return cache.get(key);
    }

    @Override
    public V findByRoute(Object[] route) {
        V v = cache.get((K) route[level]);
        return v;
    }

    @Override
    public V delById(K key) {
        return cache.remove(key);
    }

    @Override
    public V delByRoute(Object[] route) {
        return null;
    }

    @Override
    public V refreshByKey(K key) {
        return null;
    }

    @Override
    public V refreshByRoute(Object[] route) {
        return null;
    }

    @Override
    public boolean compressable() {
        return compressable;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setMtc(boolean b) {
        isMtc.set(b);
    }

    @Override
    public boolean isMtc() {
        return isMtc.get();
    }
}
