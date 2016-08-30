package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Annotations.Mvvm;
import net.gy.SwiftFrameWork.MVVM.Impl.MethodParse;

import java.util.List;

/**
 * Created by pc on 16/8/30.
 */
public class MvvmCacheControl {
    public static MvvmCache getCache(Class inf){
        MvvmCache cache = MvvmCache.getCacheMap().get(inf);
        if (cache == null){
            cache = new MvvmCache();
            List<MethodCache> list = MethodParse.getMethodCaches(inf);
            cache.setMethodCaches(list);
            MvvmCache.getCacheMap().put(inf,cache);
        }
        return cache;
    }
}
