package net.gy.SwiftFrameWork.Service.cache;

import net.gy.SwiftFrameWork.Service.cache.config.PoolType;

/**
 * Created by pc on 16/8/9.
 */
public interface ICachePool <K,V>{
    public void init(PoolType type);
    public void recycle();
    public long getSize();
    public void compress(int FLAG);
    public ICachePool getParent();
    public boolean put(Object[] key,V v);
    public boolean putPool(Object[] key,ICachePool v);
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
