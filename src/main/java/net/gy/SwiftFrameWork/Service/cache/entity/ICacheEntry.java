package net.gy.SwiftFrameWork.Service.cache.entity;

/**
 * Created by gy on 2015/11/5.
 */
public interface ICacheEntry<K, V> {

    public boolean put(K key, V value);

    public V get(K key);

    public V remove(K k);

    public void clear();

    public void setlimit(long size);

}
