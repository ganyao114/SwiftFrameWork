package net.gy.SwiftFrameWork.Service.cache;

/**
 * Created by pc on 16/8/9.
 */
public interface ICachePool <K,V>{
    public void init();
    public void recycle();
    public long getSize();
    public void compress(int FLAG);
    public ICachePool getParent();
    public V findByKey(K key);
    public V findByRoute(Object[] route);
    public V delById(K key);
    public V delByRoute(Object[] route);
    public V refreshByKey(K key);
    public V refreshByRoute(Object[] route);
    public boolean compressable();
    public int getLevel();
    public void setLevel(int level);
    public void setMtc(boolean b);
    public boolean isMtc();
}
