package net.gy.SwiftFrameWork.Service.cache.entity;

/**
 * Created by pc on 16/8/10.
 */
public interface ICache<K,V> extends Cloneable{
    public long getSize();
    public long compress();
    //回收缓存池
    public long recycle();
    //销毁缓存池
    public void destory();
    public V getValue();
    public V getKey();
    public void setValue(V v);
    public void setKey();
}
