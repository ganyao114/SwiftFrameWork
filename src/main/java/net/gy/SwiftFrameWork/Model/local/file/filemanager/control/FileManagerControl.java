package net.gy.SwiftFrameWork.Model.local.file.filemanager.control;

import net.gy.SwiftFrameWork.Model.local.file.filemanager.IFileManager;

import java.io.File;

/**
 * Created by gy on 2015/11/7.
 */
public class FileManagerControl implements IFileManager {

    private static FileManagerControl fileManagerControl;

    private static IFileManager fileManagerStrategy;

    public static FileManagerControl getInstance() {
        synchronized (FileManagerControl.class){
            if (fileManagerControl == null)
                fileManagerControl = new FileManagerControl();
        }
        return fileManagerControl;
    }

    public static void setStrategy(IFileManager strategy) {
        synchronized (fileManagerStrategy) {
            fileManagerStrategy = strategy;
        }
    }

    @Override
    public boolean newFolder(String Path) throws Exception {
        return false;
    }

    @Override
    public boolean newFile(String Path) throws Exception {
        return false;
    }

    @Override
    public boolean copyFile(String src, String target) throws Exception {
        return false;
    }

    @Override
    public boolean moveFile(String src, String target) throws Exception {
        return false;
    }

    @Override
    public boolean copyFolder(String src, String target) throws Exception {
        return false;
    }

    @Override
    public boolean moveFolder(String src, String target) throws Exception {
        return false;
    }

    @Override
    public boolean deleteFile(String src) throws Exception {
        return false;
    }

    @Override
    public boolean deleteFolder(String src) throws Exception {
        return false;
    }

    @Override
    public String getFileSize(File file) throws Exception {
        return null;
    }
}
