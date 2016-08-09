package net.gy.SwiftFrameWork.Service.cache.strategy;

import android.content.Context;
import android.os.Environment;

import net.gy.SwiftFrameWork.Service.cache.IDiskCache;

import java.io.File;

/**
 * Created by gy on 2015/11/16.
 */
public class SimpleDiskCache<K, V> implements IDiskCache<K, V> {

    private File cacheDir;

    public SimpleDiskCache(Context context) {
        establishCache(context);
    }

    private boolean establishCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "booklist");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        if (!cacheDir.exists())
            return false;
        return true;
    }

    private File getFile(String url) {
        String FileName = String.valueOf(url.hashCode());
        //String filename = URLEncoder.encode(url);
        File file = new File(cacheDir, FileName);
        return file;
    }

    @Override
    public boolean put(K key, V value) {
        return false;
    }

    @Override
    public void remove(K k) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void clear() {

    }
}
