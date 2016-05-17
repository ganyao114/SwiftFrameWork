package net.gy.SwiftFrameWork.Service.loader.downloader.impl;

import android.content.Context;

import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DWFileInfo;

/**
 * Created by gy on 2015/11/14.
 */
public class SingleDownloadTask {
    private Context mContext;
    private DWFileInfo mfileInfo;

    public SingleDownloadTask(Context mContext, DWFileInfo mfileInfo) {
        this.mContext = mContext;
        this.mfileInfo = mfileInfo;
    }


}
