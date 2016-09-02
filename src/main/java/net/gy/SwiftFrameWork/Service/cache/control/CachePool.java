package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.config.PoolType;
import net.gy.SwiftFrameWork.Service.cache.entity.ICacheEntry;
import net.gy.SwiftFrameWork.Service.cache.entity.RootCachePool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by pc on 16/8/9.
 */
public class CachePool<K,V> implements ICachePool<K,V>{

    public volatile static ICachePool poolRoot;

    private CachePoolGroup parent;

    protected ICacheEntry<K,V> cache;

    protected int level = 0;

    private boolean compressable = false;

    public AtomicBoolean isMtc = new AtomicBoolean(false);

//    public static ICachePool buildPool(){
//        if (poolRoot == null){
//            synchronized (CachePool.class){
//                if (poolRoot == null){
//                    establish();
//                }
//            }
//        }
//        return poolRoot;
//    }
//
//    private static void establish() {
//
//    }

    @Override
    public void init(PoolType type) {

    }

    @Override
    public void recycle() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void obtain() {

    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public void compress(int FLAG) {

    }


    @Override
    public ICachePool getParent() {
        return parent;
    }

    @Override
    public boolean put(Object[] key, V v) {
        if (key.length != level+1)
            return false;
        cache.put((K) key[level],v);
        return true;
    }

    @Override
    public boolean putPool(Object[] key, ICachePool v) {
        return false;
    }

    @Override
    public V findByKey(K key) {
        return cache.get(key);
    }

    @Override
    public V findByRoute(Object[] route) {
        if (route.length != level+1)
            return null;
        V v = cache.get((K) route[level]);
        return v;
    }

    @Override
    public V delById(K key) {
        return cache.remove(key);
    }

    @Override
    public V delByRoute(Object[] route) {
        if (route.length != level+1)
            return null;
        return cache.remove((K) route[level]);
    }

    @Override
    public V refreshByKey(K key) {
        V v = cache.get(key);
        return v;
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

    public static ICachePool getRoot(){
        return poolRoot;
    }

    public synchronized static void initPool(PoolType Type){
        if (poolRoot == null){
            poolRoot = new RootCachePool();
            poolRoot.init(Type);
        }
    }

}
