package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.control.CachePool;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/21.
 */
public class ReflectClassCache extends CachePool<Class,Map<Class<? extends Annotation>,Annotation>>{

    public ReflectClassCache() {
        cache = new ClassCache();
    }

}
