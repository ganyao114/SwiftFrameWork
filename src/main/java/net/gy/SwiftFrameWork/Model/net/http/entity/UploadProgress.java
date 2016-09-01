package net.gy.SwiftFrameWork.Model.net.http.entity;

/**
 * Created by gy on 2016/2/25.
 */
public class UploadProgress {

    public int persent;
    private long finished;

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public int getPersent() {
        return persent;
    }

    public void setPersent(int persent) {
        this.persent = persent;
    }
}
