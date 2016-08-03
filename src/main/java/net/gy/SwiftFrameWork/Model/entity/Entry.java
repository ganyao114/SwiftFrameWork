package net.gy.SwiftFrameWork.Model.entity;

/**
 * Created by pc on 16/8/2.
 */
public interface Entry<K,V> {
    public void set(K k,V v);
    public K getKey();
    public V getValue();
}
