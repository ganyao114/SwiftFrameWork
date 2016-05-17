package net.gy.SwiftFrameWork.Service.loader.downloader.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by gy on 2015/11/9.
 */
public class DWFileInfo implements Serializable {
    private int id;
    private String filename;
    private String url;
    private int size;
    private int finished;
    private int blocksize;
    private int blocks;
    private int currentblockid;
    private Vector<BlockInfo> badblock;
    private int currentThread;

    public synchronized int getCurrentThread() {
        return currentThread;
    }

    public synchronized void setCurrentThread(int currentThread) {
        this.currentThread = currentThread;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public void setBlocksize(int blocksize) {
        this.blocksize = blocksize;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getCurrentblockid() {
        return currentblockid;
    }

    public void setCurrentblockid(int currentblockid) {
        this.currentblockid = currentblockid;
    }

    public Vector<BlockInfo> getBadblock() {
        return badblock;
    }

    public void setBadblock(Vector<BlockInfo> badblock) {
        this.badblock = badblock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
