package net.gy.SwiftFrameWork.Service.cache.control;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.config.PoolType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 16/8/9.
 */
public class CachePoolGroup<K> extends CachePool{

    protected Map<K,ICachePool> poolMap;


    @Override
    public Object delById(Object key) {
        Object value = poolMap.get(key);
        if (value != null)
        {
            return poolMap.remove(key);
        }
        return super.delById(key);
    }

    @Override
    public Object delByRoute(Object[] route) {
        if (route.length == level + 1)
            return poolMap.remove(route[level]);
        ICachePool cachePool = poolMap.get(route[level]);
        if (cachePool == null)
            return null;
        else
            return cachePool.delByRoute(route);
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
    public boolean putPool(Object[] key, ICachePool v) {

        if (key.length == level + 1){
            if (poolMap.containsKey(key[level]))
                return false;
            v.setLevel(level + 1);
            poolMap.put((K) key[level],v);
        }else if (key.length > level + 1){
            if (poolMap.containsKey(key[level]))
                return false;
            ICachePool cachePool = poolMap.get(key[level]);
            cachePool.putPool(key, v);
        }else {
            return false;
        }
        return true;
    }

    @Override
    public boolean put(Object[] key, Object o) {
        if (key.length > level + 1){
            if (!poolMap.containsKey(key[level]))
                return false;
            ICachePool cachePool = poolMap.get(key[level]);
            cachePool.put(key, o);
        }else {
            return false;
        }
        return true;
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
    public void init(PoolType type) {
        if (type == PoolType.Sync){
            poolMap = new ConcurrentHashMap<>();
        }else {
            poolMap = new HashMap<>();
        }
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
