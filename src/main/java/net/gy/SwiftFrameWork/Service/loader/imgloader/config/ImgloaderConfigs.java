package net.gy.SwiftFrameWork.Service.loader.imgloader.config;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.ImgLoadConfigs;

/**
 * Created by gy on 2015/11/7.
 */
public class ImgloaderConfigs {
    private int waitImgsrc;
    private int Xmax;
    private int Ymax;
    private ImgLoadConfigs cacheConfigs;

    public int getWaitImgsrc() {
        return waitImgsrc;
    }

    public void setWaitImgsrc(int waitImgsrc) {
        this.waitImgsrc = waitImgsrc;
    }

    public int getXmax() {
        return Xmax;
    }

    public void setXmax(int xmax) {
        Xmax = xmax;
    }

    public int getYmax() {
        return Ymax;
    }

    public void setYmax(int ymax) {
        Ymax = ymax;
    }

    public ImgLoadConfigs getCacheConfigs() {
        return cacheConfigs;
    }

    public void setCacheConfigs(ImgLoadConfigs cacheConfigs) {
        this.cacheConfigs = cacheConfigs;
    }
}
