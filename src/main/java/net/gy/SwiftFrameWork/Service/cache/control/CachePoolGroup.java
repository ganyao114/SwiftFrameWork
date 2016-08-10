package net.gy.SwiftFrameWork.Service.cache.control;

import java.util.Map;

/**
 * Created by pc on 16/8/9.
 */
public class CachePoolGroup<K> extends CachePool{
    private Map<K,CachePool> poolMap;
    public void addPool(CachePool pool){

    }

    @Override
    public Object delById(Object key) {
        return super.delById(key);
    }

    @Override
    public Object delByRoute(String route) {
        return super.delByRoute(route);
    }

    @Override
    public Object findByRoute(String route) {
        return super.findByRoute(route);
    }

    @Override
    public Object findByKey(Object key) {
        return super.findByKey(key);
    }

    @Override
    public void compress() {
        super.compress();
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
        CachePool pool = poolMap.get(null);
        if (pool instanceof CachePoolGroup){

        }else {
            t = (T) pool.findByKey(null);
        }
        return t;
    }
}
