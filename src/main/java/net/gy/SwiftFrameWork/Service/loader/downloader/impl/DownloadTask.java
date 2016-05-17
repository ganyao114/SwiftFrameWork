package net.gy.SwiftFrameWork.Service.loader.downloader.impl;

import android.content.Context;
import android.content.Intent;

import net.gy.SwiftFrameWork.Service.loader.downloader.db.ThreadDAO;
import net.gy.SwiftFrameWork.Service.loader.downloader.db.ThreadDAOImpl;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.FileInfo;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.ThreadInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class DownloadTask {
    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private int mFinised = 0;
    public boolean isPause = false;


    public DownloadTask(Context mContext, FileInfo mFileInfo) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        mDao = new ThreadDAOImpl(mContext);
    }

    public void downLoad() {

        List<ThreadInfo> threads = mDao.getThreads(mFileInfo.getUrl());
        ThreadInfo threadInfo = null;

        if (0 == threads.size()) {

            threadInfo = new ThreadInfo(0, mFileInfo.getUrl(),
                    0, mFileInfo.getLength(), 0);
        } else {
            threadInfo = threads.get(0);
        }


        new DownloadThread(threadInfo).start();
    }


    private class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo = null;


        public DownloadThread(ThreadInfo mInfo) {
            this.mThreadInfo = mInfo;
        }


        @Override
        public void run() {

            if (!mDao.isExists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mDao.insertThread(mThreadInfo);
            }

            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;

            try {
                URL url = new URL(mThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");

                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                connection.setRequestProperty("Range",
                        "bytes=" + start + "-" + mThreadInfo.getEnd());

                File file = new File(DownloadService.DOWNLOAD_PATH,
                        mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent();
                intent.setAction(DownloadService.ACTION_UPDATE);
                mFinised += mThreadInfo.getFinished();

                if (connection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {

                    inputStream = connection.getInputStream();
                    byte buf[] = new byte[1024 << 2];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buf)) != -1) {

                        raf.write(buf, 0, len);

                        mFinised += len;
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", mFinised * 100 / mThreadInfo.getEnd());
                            mContext.sendBroadcast(intent);
                        }


                        if (isPause) {
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinised);
                            return;
                        }
                    }


                    mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());

                    //MainActivity.mMainActivity.handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
