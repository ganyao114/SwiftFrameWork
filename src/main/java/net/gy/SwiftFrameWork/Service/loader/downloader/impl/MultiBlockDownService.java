package net.gy.SwiftFrameWork.Service.loader.downloader.impl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DWFileInfo;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DownloadThreadRes;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.FileInfo;
import net.gy.SwiftFrameWork.Service.loader.downloader.thread.BaseDownloadThread;

import java.util.Vector;

/**
 * Created by gy on 2015/11/14.
 */
public class MultiBlockDownService extends Service {

    private int MAX_THREAD = 4;
    private BaseDownloadThread[] downloadThreads;
    private Vector<DownloadThreadRes> swreslist;
    private Vector<DWFileInfo> Downloadtask;
    private Vector<DownloadThreadRes> swreslistWk;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized DownloadThreadRes getDownRes() {

        DownloadThreadRes resreturn;
        synchronized (swreslistWk) {
            if (swreslistWk.size() > 0) {
                resreturn = swreslist.get(0);
                swreslistWk.remove(0);
                return resreturn;
            }
        }

        DWFileInfo TargetFile = Downloadtask.get(0);

        for (DWFileInfo fileInfo : Downloadtask) {
            if (fileInfo.getCurrentThread() < TargetFile.getCurrentThread())
                TargetFile = fileInfo;
        }


        return null;
    }

    private synchronized void returnDoenRes(DownloadThreadRes res) {
        synchronized (swreslistWk) {
            swreslistWk.add(res);
        }
    }

    private DownloadThreadRes initDownRes(FileInfo fileInfo, int SeekPosition) {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
