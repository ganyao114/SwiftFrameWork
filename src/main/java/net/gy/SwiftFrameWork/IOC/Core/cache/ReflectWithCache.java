package net.gy.SwiftFrameWork.IOC.Core.cache;

import net.gy.SwiftFrameWork.Service.cache.control.CachePool;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by pc on 16/8/22.
 */
public class ReflectWithCache {

    private static ReflectCacheControl cacheControl = ReflectCacheControl.getInstance();


    public static Map<Class<? extends Annotation>,Annotation> getClassAnnoWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.clazz,clazz};
        Object annotations = CachePool.getRoot().findByRoute(route);
        if (annotations == null)
            cacheControl.decodeClass(clazz);
        return (Map<Class<? extends Annotation>,Annotation>) CachePool.getRoot().findByRoute(route);
    }

    public static FieldEntity[] getFieldsWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.field,clazz};
        Object entities = CachePool.getRoot().findByRoute(route);
        if (entities == null)
            cacheControl.decodeFeild(clazz);
        return (FieldEntity[]) CachePool.getRoot().findByRoute(route);
    }

    public static MethodEntity[] getMethodsWithType(Class clazz){
        Object[] route = new Object[]{ReflectCacheRoute.methed,clazz};
        Object entities = CachePool.getRoot().findByRoute(route);
        if (entities == null)
            cacheControl.decodeMethod(clazz);
        return (MethodEntity[]) CachePool.getRoot().findByRoute(route);
    }




}
