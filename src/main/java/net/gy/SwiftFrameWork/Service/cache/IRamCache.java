package net.gy.SwiftFrameWork.Service.cache;

/**
 * Created by gy on 2015/11/5.
 */
public interface IRamCache<K, V> {
    public boolean put(K key, V value);

    public V get(K key);

    public void clear();

    public void setlimit(long size);
}
