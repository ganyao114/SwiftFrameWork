package net.gy.SwiftFrameWork.Service.loader.downloader.entity;

import android.content.Intent;

/**
 * Created by gy on 2015/11/9.
 */
public class DownloadThreadRes {

    private DWFileInfo fileInfo;
    private int id;
    private DwDaoEntity dao;
    private Intent intent;
    private boolean isUsing;


    public DownloadThreadRes() {
    }

    public DownloadThreadRes(DWFileInfo fileInfo, int id, DwDaoEntity dao, Intent intent, boolean isUsing) {
        this.fileInfo = fileInfo;
        this.id = id;
        this.dao = dao;
        this.intent = intent;
        this.isUsing = isUsing;
    }

    public boolean getIsUsing() {
        return isUsing;
    }

    public synchronized void setIsUsing(boolean isUsing) {
        this.isUsing = isUsing;
    }

    public DWFileInfo getFileInfo() {
        return fileInfo;
    }

    public synchronized void setFileInfo(DWFileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DwDaoEntity getDao() {
        return dao;
    }

    public void setDao(DwDaoEntity dao) {
        this.dao = dao;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
