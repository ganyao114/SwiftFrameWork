package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.cache;

import android.graphics.Bitmap;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MemoryCache {
    private Map<String, Bitmap> cache = Collections
            .synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
    private long size = 0;
    private long limit = 10000000;

    public MemoryCache() {
        // TODO Auto-generated constructor stub
    }

    public void setLimit(long _limit) {
        this.limit = _limit;
    }

    public Bitmap get(String id) {
        try {
            if (!cache.containsKey(id))
                return null;
            else
                return cache.get(id);
        } catch (NullPointerException e) {
            // TODO: handle exception
            return null;
        }
    }

    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache.get(id));
            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        } catch (NullPointerException e) {
            // TODO: handle exception
        }
    }

    private void checkSize() {
        if (size > limit) {
            Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Bitmap> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (size <= limit)
                    break;
            }

        }
    }

    public void clear() {
        cache.clear();
    }

    public void remove(String key){
        if (cache.containsKey(key)) {
            size -= getSizeInBytes(cache.remove(key));
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
