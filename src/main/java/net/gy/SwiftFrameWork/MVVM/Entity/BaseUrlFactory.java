package net.gy.SwiftFrameWork.MVVM.Entity;

import net.gy.SwiftFrameWork.utils.SyncHashMap;
import net.gy.SwiftFrameWork.utils.SyncMapProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础Url工厂
 * Created by gy939 on 2016/9/12.
 */
public class BaseUrlFactory {

    private static Map<String,String> urlMap = SyncMapProxy.SyncMap(new HashMap<String, String>());

    public static Map<String, String> getUrlMap() {
        return urlMap;
    }

    public static void addUrl(String key,String url){
        urlMap.put(key, url);
    }

    public static String getUrl(String key){
        return urlMap.get(key);
    }

}
