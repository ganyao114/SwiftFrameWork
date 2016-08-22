package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.control.CachePool;

import java.util.List;

/**
 * Created by pc on 16/8/20.
 */
public class ReflectFieldCache extends CachePool<Class,FieldEntity[]>{

    public ReflectFieldCache() {
        cache = new FieldCache();
    }
}
