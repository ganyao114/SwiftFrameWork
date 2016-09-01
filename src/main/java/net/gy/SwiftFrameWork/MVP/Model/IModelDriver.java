package net.gy.SwiftFrameWork.MVP.Model;

/**
 * Created by gy on 2016/3/25.
 */
public interface IModelDriver<K,V> {
    public V getData(K k);
    public V getData();
}
