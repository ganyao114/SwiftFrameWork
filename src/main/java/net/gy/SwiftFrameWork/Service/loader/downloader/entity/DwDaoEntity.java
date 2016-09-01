package net.gy.SwiftFrameWork.Service.loader.downloader.entity;

import net.gy.SwiftFrameWork.Service.loader.downloader.IDownloadDB;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

/**
 * Created by gy on 2015/11/14.
 */
public class DwDaoEntity {
    private IDownloadDB db;
    private HttpURLConnection connection;
    private RandomAccessFile raf;
    private InputStream inputStream;

    public DwDaoEntity(IDownloadDB db, HttpURLConnection connection, RandomAccessFile raf, InputStream inputStream) {
        this.db = db;
        this.connection = connection;
        this.raf = raf;
        this.inputStream = inputStream;
    }

    public DwDaoEntity() {
    }

    public IDownloadDB getDb() {
        return db;
    }

    public void setDb(IDownloadDB db) {
        this.db = db;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public RandomAccessFile getRaf() {
        return raf;
    }

    public void setRaf(RandomAccessFile raf) {
        this.raf = raf;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
