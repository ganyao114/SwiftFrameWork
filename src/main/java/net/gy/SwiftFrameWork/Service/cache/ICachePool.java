package net.gy.SwiftFrameWork.Service.cache;

/**
 * Created by pc on 16/8/9.
 */
public interface ICachePool <K,V>{
    public void init();
    public void recycle();
    public long getSize();
    public void compress();
    public ICachePool getParent();
    public V findByKey(K key);
    public V findByRoute(String route);
    public V delById(K key);
    public V delByRoute(String route);
    public V refreshByKey(K key);
    public V refreshByRoute(String route);
    public boolean compressable();
}
