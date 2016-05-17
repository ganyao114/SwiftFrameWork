package net.gy.SwiftFrameWork.Service.loader.downloader.thread;

import net.gy.SwiftFrameWork.Service.loader.downloader.IDownloadDB;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DWFileInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gy on 2015/11/9.
 */
public class SingleThreadDownloadThread implements Runnable {

    private DWFileInfo fileInfo;
    private String dir;
    private IDownloadDB dao;

    public SingleThreadDownloadThread(DWFileInfo fileInfo, String dir, IDownloadDB dao) {
        this.fileInfo = fileInfo;
        this.dir = dir;
        this.dao = dao;
    }

    @Override
    public void run() {

        HttpURLConnection conn;
        RandomAccessFile rdf;
        try {
            URL url = new URL(fileInfo.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            int length = -1;
            if (conn.getResponseCode() == HttpStatus.SC_OK) {
                length = conn.getContentLength();
            }
            if (length <= 0)
                return;
            File file = new File(dir, fileInfo.getFilename());
            rdf = new RandomAccessFile(file, "rwd");

            rdf.setLength(length);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
