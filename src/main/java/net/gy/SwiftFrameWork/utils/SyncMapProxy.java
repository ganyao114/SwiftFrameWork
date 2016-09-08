package net.gy.SwiftFrameWork.utils;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by gy on 2016/9/8.
 */
public class SyncMapProxy<K,V> implements Map<K,V>{

    private Map<K,V> origin;
    private ReadWriteLock lock;

    public SyncMapProxy(Map<K, V> origin) {
        this.origin = origin;
        lock = new ReentrantReadWriteLock();
    }

    public static  <K,V> SyncMapProxy<K,V> SyncMap(Map<K,V> map){
        return new SyncMapProxy<K,V>(map);
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        origin.clear();
        lock.writeLock().unlock();
    }

    @Override
    public boolean containsKey(Object key) {
        lock.readLock().lock();
        boolean res = origin.containsKey(key);
        lock.readLock().unlock();
        return res;
    }

    @Override
    public boolean containsValue(Object value) {
        lock.readLock().lock();
        boolean res = origin.containsKey(value);
        lock.readLock().unlock();
        return res;
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        lock.readLock().lock();
        Set<Entry<K, V>> res = origin.entrySet();
        lock.readLock().unlock();
        return res;
    }

    @Override
    public V get(Object key) {
        lock.readLock().lock();
        V res = origin.get(key);
        lock.readLock().unlock();
        return res;
    }

    @Override
    public boolean isEmpty() {
        return origin.isEmpty();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        lock.readLock().lock();
        Set<K> res = origin.keySet();
        lock.readLock().unlock();
        return res;
    }

    @Override
    public V put(K key, V value) {
        lock.writeLock().lock();
        V v = origin.put(key, value);
        lock.writeLock().unlock();
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        lock.writeLock().lock();
        origin.putAll(map);
        lock.writeLock().unlock();
    }

    @Override
    public V remove(Object key) {
        lock.writeLock().lock();
        V v = origin.remove(key);
        lock.writeLock().unlock();
        return v;
    }

    @Override
    public int size() {
        return origin.size();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        lock.readLock().lock();
        Collection<V> res = origin.values();
        lock.readLock().unlock();
        return res;
    }
}
