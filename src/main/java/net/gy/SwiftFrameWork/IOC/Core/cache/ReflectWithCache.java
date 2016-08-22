package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.control.CachePool;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by pc on 16/8/22.
 */
public class ReflectWithCache {

    private ReflectCacheControl cacheControl;

    public Map getClassAnnoWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.clazz,clazz};
        Object annotations = CachePool.getRoot().findByRoute(route);
        if (annotations == null)
            cacheControl.decodeClass(clazz);
        return (Map) CachePool.getRoot().findByRoute(route);
    }

    public FieldEntity[] getFieldsWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.field,clazz};
        Object entities = CachePool.getRoot().findByRoute(route);
        if (entities == null)
            cacheControl.decodeFeild(clazz);
        return (FieldEntity[]) CachePool.getRoot().findByRoute(route);
    }

    public MethodEntity[] getMethodsWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.methed,clazz};
        Object entities = CachePool.getRoot().findByRoute(route);
        if (entities == null)
            cacheControl.decodeFeild(clazz);
        return (MethodEntity[]) CachePool.getRoot().findByRoute(route);
    }


}
