package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;

import java.util.Map;

/**
 * Created by pc on 16/8/9.
 */
public class CachePoolGroup<K> extends CachePool{
    private Map<K,ICachePool> poolMap;
    public void addPool(CachePool pool){

    }

    @Override
    public Object delById(Object key) {
        return super.delById(key);
    }

    @Override
    public Object delByRoute(Object[] route) {
        return super.delByRoute(route);
    }

    @Override
    public Object findByRoute(Object[] route) {
        ICachePool cachePool = poolMap.get(route[level]);
        if (cachePool == null)
            return null;
        return cachePool.findByRoute(route);
    }

    @Override
    public Object findByKey(Object key) {
        if (poolMap.containsKey(key))
            return poolMap.get(key);
        for (Map.Entry<K,ICachePool> entry:poolMap.entrySet()){
            ICachePool cachePool = entry.getValue();
            Object object = cachePool.findByKey(key);
            if (object!=null)
                return object;
        }
        return null;
    }

    @Override
    public void compress(int FLAG) {
        super.compress(FLAG);
    }

    @Override
    public long getSize() {
        return super.getSize();
    }

    @Override
    public void recycle() {
        super.recycle();
    }

    @Override
    public void init() {
        super.init();
    }

    public <I,T> T dispatchCache(I id){
        T t = null;
        ICachePool pool = poolMap.get(null);
        if (pool instanceof CachePoolGroup){

        }else {
            t = (T) pool.findByKey(null);
        }
        return t;
    }
}
