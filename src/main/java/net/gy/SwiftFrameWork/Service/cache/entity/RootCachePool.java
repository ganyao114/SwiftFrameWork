package net.gy.SwiftFrameWork.Service.cache.entity;

import net.gy.SwiftFrameWork.Service.cache.ICachePool;
import net.gy.SwiftFrameWork.Service.cache.config.PoolType;
import net.gy.SwiftFrameWork.Service.cache.control.CachePoolGroup;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 16/8/20.
 */
public class RootCachePool extends CachePoolGroup{

    public void init(PoolType type){
        poolMap = new ConcurrentHashMap<Object,ICachePool>();
        poolRoot = this;
    }



}
