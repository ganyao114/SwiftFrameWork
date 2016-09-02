package net.gy.SwiftFrameWork.Service.cache.strategy;

import android.util.LruCache;

/**
 * Created by gy on 2016/9/3.
 */
public class LruCacheTest extends LruCache{
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruCacheTest(int maxSize) {
        super(maxSize);
    }
}
