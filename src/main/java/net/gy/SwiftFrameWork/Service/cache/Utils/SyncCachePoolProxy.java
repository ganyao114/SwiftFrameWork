package net.gy.SwiftFrameWork.Service.cache.Utils;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.config.PoolType;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by gy939 on 2016/9/8.
 */
public class SyncCachePoolProxy<K,V> implements ICachePool<K,V>{

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private ICachePool<K,V> origin;

    public SyncCachePoolProxy(ICachePool<K, V> origin) {
        this.origin = origin;
    }

    @Override
    public void init(PoolType type) {

    }

    @Override
    public void recycle() {
        lock.writeLock().lock();
        origin.recycle();
        lock.writeLock().unlock();
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
        return null;
    }

    @Override
    public boolean put(Object[] key, V v) {
        return false;
    }

    @Override
    public boolean putPool(Object[] key, ICachePool v) {
        return false;
    }

    @Override
    public V findByKey(K key) {
        return null;
    }

    @Override
    public V findByRoute(Object[] route) {
        return null;
    }

    @Override
    public V delById(K key) {
        return null;
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
        return false;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void setLevel(int level) {

    }

    @Override
    public void setMtc(boolean b) {

    }

    @Override
    public boolean isMtc() {
        return false;
    }
}
