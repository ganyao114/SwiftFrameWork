package net.gy.SwiftFrameWork.Service.cache.entity;

import java.util.Set;

/**
 * Created by gy on 2015/11/5.
 */
public interface ICacheEntry<K, V> {

    public boolean put(K key, V value);

    public V get(K key);

    public Set<K> getKeys();

    public V remove(K k);

    public void clear();

    public void setlimit(long size);

    public long compress();

    public void swapout();

    public void swapin();

    public ICacheEntry<K,V> getEntry();


}
