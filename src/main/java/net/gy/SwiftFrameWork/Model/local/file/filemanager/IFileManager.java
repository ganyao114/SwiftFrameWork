package net.gy.SwiftFrameWork.Model.local.file.filemanager;

import java.io.File;

/**
 * Created by gy on 2015/11/7.
 */
public interface IFileManager {
    public boolean newFolder(String Path) throws Exception;

    public boolean newFile(String Path) throws Exception;

    public boolean copyFile(String src, String target) throws Exception;

    public boolean moveFile(String src, String target) throws Exception;

    public boolean copyFolder(String src, String target) throws Exception;

    public boolean moveFolder(String src, String target) throws Exception;

    public boolean deleteFile(String src)throws Exception;

    public boolean deleteFolder(String src)throws Exception;

    public String getFileSize(File file)throws Exception;
}
