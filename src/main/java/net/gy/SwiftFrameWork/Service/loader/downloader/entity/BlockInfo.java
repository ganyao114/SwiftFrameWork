package net.gy.SwiftFrameWork.Service.loader.downloader.entity;

/**
 * Created by gy on 2015/11/14.
 */
public class BlockInfo {
    private int fileid;
    private String url;
    private int blockid;
    private int blocksize;
    private int flag;

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBlockid() {
        return blockid;
    }

    public void setBlockid(int blockid) {
        this.blockid = blockid;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public void setBlocksize(int blocksize) {
        this.blocksize = blocksize;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
