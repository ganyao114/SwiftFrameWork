package net.gy.SwiftFrameWork.Service.cache.entity;

/**
 * Created by pc on 16/8/10.
 */
public interface ICache<K,V> extends Cloneable{
    public long getSize();
    public long compress();
    public long recycle();
    public V getValue();
    public V getKey();
    public void setValue(V v);
    public void setKey();
}
