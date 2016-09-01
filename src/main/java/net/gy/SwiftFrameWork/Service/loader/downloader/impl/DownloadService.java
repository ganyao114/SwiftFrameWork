
package net.gy.SwiftFrameWork.Service.loader.downloader.impl;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import net.gy.SwiftFrameWork.Service.loader.downloader.entity.FileInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadService extends Service {
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/downloads/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final int MSG_INIT = 0;
    private DownloadTask mTask = null;
    private String TAG = "DownloadService";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i(TAG, "Start:" + fileInfo.toString());

            new InitThread(fileInfo).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i(TAG, "Stop:" + fileInfo.toString());

            if (mTask != null) {
                mTask.isPause = true;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i(TAG, "Init:" + fileInfo);
                    mTask = new DownloadTask(DownloadService.this, fileInfo);
                    mTask.downLoad();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private class InitThread extends Thread {
        private FileInfo mFileInfo = null;

        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }


        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;

            try {

                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1;

                if (connection.getResponseCode() == HttpStatus.SC_OK) {

                    length = connection.getContentLength();
                }

                if (length <= 0) {
                    return;
                }

                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }


                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");

                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
