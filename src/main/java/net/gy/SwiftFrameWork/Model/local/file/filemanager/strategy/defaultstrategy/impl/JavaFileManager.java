package net.gy.SwiftFrameWork.Model.local.file.filemanager.strategy.defaultstrategy.impl;

import net.gy.SwiftFrameWork.Model.local.file.filemanager.IFileManager;
import net.gy.SwiftFrameWork.Model.local.file.filemanager.IFileManagerCallback;
import net.gy.SwiftFrameWork.Model.local.file.filemanager.entity.TmpFileInfo;
import net.gy.SwiftFrameWork.Model.local.file.filemanager.strategy.defaultstrategy.raw.RawFileManager;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.io.File;

/**
 * Created by gy on 2015/11/7.
 */
public class JavaFileManager implements IFileManager,IFileManagerCallback {

    private IFileManager fmRow;
    private IFileManagerCallback callback;

    public JavaFileManager(IFileManager fmRow, IFileManagerCallback callback) {
        this.fmRow = fmRow;
        this.callback = callback;
        init();
    }

    private void init(){
        RawFileManager rwfilemanager = (RawFileManager) fmRow;
        rwfilemanager.setCallBack(this);
    }

    @Override
    public boolean newFolder(String Path) throws Exception {
        return fmRow.newFolder(Path);
    }

    @Override
    public boolean newFile(String Path) throws Exception {
        return fmRow.newFile(Path);
    }

    @Override
    public boolean copyFile(final String src, final String target) throws Exception {
        MyWorkThreadQueue.AddTask(new Runnable() {
            @Override
            public void run() {
                try {
                    fmRow.copyFile(src,target);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Override
    public boolean moveFile(String src, String target) throws Exception {
        fmRow.moveFile(src,target);
        return false;
    }

    @Override
    public boolean copyFolder(final String src, final String target) throws Exception {
        MyWorkThreadQueue.AddTask(new Runnable() {
            @Override
            public void run() {
                try {
                    fmRow.copyFolder(src,target);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Override
    public boolean moveFolder(final String src, final String target) throws Exception {
        MyWorkThreadQueue.AddTask(new Runnable() {
            @Override
            public void run() {
                try {
                    fmRow.moveFolder(src,target);
                    TmpFileInfo info = new TmpFileInfo();
                    info.setPath(src);
                    info.setOperateType(TmpFileInfo.OperateType.MOVE_FOLDER);
                    callback.onCompelet(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Override
    public boolean deleteFile(final String src) throws Exception {
        MyWorkThreadQueue.AddTask(new Runnable() {
            @Override
            public void run() {
                try {
                    fmRow.deleteFile(src);
                    TmpFileInfo info = new TmpFileInfo();
                    info.setPath(src);
                    info.setOperateType(TmpFileInfo.OperateType.DELETE_FILE);
                    callback.onCompelet(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Override
    public boolean deleteFolder(final String src) throws Exception {
        MyWorkThreadQueue.AddTask(new Runnable() {
            @Override
            public void run() {
                try {
                    fmRow.deleteFolder(src);
                    TmpFileInfo info = new TmpFileInfo();
                    info.setPath(src);
                    info.setOperateType(TmpFileInfo.OperateType.DELETE_FOLDER);
                    callback.onCompelet(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    @Override
    public String getFileSize(File file) throws Exception {
        return fmRow.getFileSize(file);
    }


    @Override
    public void onFileOpDeration(TmpFileInfo info) {
        callback.onFileOpDeration(info);
    }

    @Override
    public void onCompelet(TmpFileInfo info) {
        callback.onCompelet(info);
    }
}
