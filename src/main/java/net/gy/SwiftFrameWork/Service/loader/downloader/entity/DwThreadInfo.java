package net.gy.SwiftFrameWork.Service.loader.downloader.entity;

/**
 * Created by gy on 2015/11/9.
 */
public class DwThreadInfo {
    private int id;
    private String url;
    private int start;
    private int end;
    private int finished;

    public DwThreadInfo(int start, int id, String url, int end, int finished) {
        this.start = start;
        this.id = id;
        this.url = url;
        this.end = end;
        this.finished = finished;
    }

    public DwThreadInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
