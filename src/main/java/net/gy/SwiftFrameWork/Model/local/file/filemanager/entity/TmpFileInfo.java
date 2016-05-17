package net.gy.SwiftFrameWork.Model.local.file.filemanager.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by gy on 2016/1/22.
 */
public class TmpFileInfo {

    private String filename;
    private String path;
    private OperateType operateType;
    private long size;
    private float persent;
    //KB
    private long finished;
    private long remained;

    public static TmpFileInfo create(File file, OperateType type){
        TmpFileInfo info = new TmpFileInfo();
        info.setFilename(file.getName());
        info.setSize(info.getFileSizeRow(file));
        info.setOperateType(type);
        return info;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public float getPersent() {
        return persent;
    }

    public void setPersent(float persent) {
        this.persent = persent;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public long getRemained() {
        return remained;
    }

    public void setRemained(long remained) {
        this.remained = remained;
    }

    public long getFileSizeRow(File file)

    {
        long size = 0;

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            size = fis.available();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return size;

    }

    public enum OperateType{
        NEW_FILE,
        NEW_FOLDER,
        COPY_FILE,
        COPY_FOLDER,
        MOVE_FILE,
        MOVE_FOLDER,
        DELETE_FILE,
        DELETE_FOLDER,
    }

}
