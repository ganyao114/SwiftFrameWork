package net.gy.SwiftFrameWork.Service.cache.impl;

/**
 * Created by pc on 16/8/10.
 */
public class Utils {
    public long getHeapSize(){
        return Runtime.getRuntime().maxMemory();
    }
}
