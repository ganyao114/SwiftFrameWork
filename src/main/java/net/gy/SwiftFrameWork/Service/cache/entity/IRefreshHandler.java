package net.gy.SwiftFrameWork.Service.cache.entity;

/**
 * Created by gy on 2016/9/6.
 */
public interface IRefreshHandler<K,V> {
    public V dorefresh(K key);
}
