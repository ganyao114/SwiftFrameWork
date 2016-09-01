package net.gy.SwiftFrameWork.Model.local.file.filemanager.strategy.defaultstrategy.raw;

import net.gy.SwiftFrameWork.Model.local.file.filemanager.IFileManager;
import net.gy.SwiftFrameWork.Model.local.file.filemanager.entity.TmpFileInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Created by gy on 2016/1/22.
 */
public class JavaRawFileManager extends RawFileManager implements IFileManager{

    private final int kb = 1024;



    @Override
    public boolean copyFile(String srcPath, String targetPath) throws Exception{

        int blockSize = 16*kb;

        TmpFileInfo info = null;

        File src = null;
        File target = null;

        InputStream in = null;
        OutputStream out = null;

        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            src = new File(srcPath);
            target = new File(targetPath);
            in = new FileInputStream(src);
            out = new FileOutputStream(target);
            bin = new BufferedInputStream(in);
            bout = new BufferedOutputStream(out);
            info = TmpFileInfo.create(src,TmpFileInfo.OperateType.COPY_FILE);
            byte[] b = new byte[blockSize];
            int len = bin.read(b);
            long count = 0;
            while (len != -1) {
                bout.write(b, 0, len);
                count++;
                copyDeration(count,blockSize,src,info);
                len = bin.read(b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
                if (bout != null) {
                    bout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        callBack.onCompelet(info);
        return true;
    }

    private void copyDeration(long count, int blockSize, File src, TmpFileInfo info) {
        info.setFinished(count * blockSize);
        callBack.onFileOpDeration(info);
    }

    public String getFileSizeMB(File file)

    {
        DecimalFormat df = new DecimalFormat("#.00");
        long size = 0;
        String sizemb;

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
        if (size / 1048576 > 0) {
            sizemb = df.format((double) size / 1048576) + "MB";
        } else {
            sizemb = "0" + df.format((double) size / 1048576) + "MB";
        }

        return sizemb;

    }
    /**
     * 返回文件大小 单位B
     * @param file
     * @return
     */
    @SuppressWarnings("resource")
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

    @Override
    public boolean newFolder(String Path) throws Exception {
        return new File(Path).mkdirs();
    }

    @Override
    public boolean newFile(String Path) throws Exception {
        return false;
    }



    @Override
    public boolean moveFile(String src, String target) throws Exception {
        return new File(src).renameTo(new File(target));
    }

    @Override
    public boolean copyFolder(String sourceDir, String targetDir) throws Exception {

        new File(targetDir).mkdirs();

        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {

                File sourceFile = file[i];

                String targetFile = new File(targetDir).getAbsolutePath() + File.separator
                                + file[i].getName();
                copyFile(sourceFile.getAbsolutePath(), targetFile);

            }
            if (file[i].isDirectory()) {

                String dir1 = sourceDir + "/" + file[i].getName();

                String dir2 = targetDir + "/" + file[i].getName();
                copyFolder(dir1, dir2);
            }
        }
        return true;
    }

    @Override
    public boolean moveFolder(String src, String target) throws Exception {
        return false;
    }

    @Override
    public boolean deleteFile(String src) throws Exception {
        File file = new File(src);
        boolean result = false;
        if (file != null) {
            try {
                File file2 = file;
                file2.delete();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean deleteFolder(String src) throws Exception {
        File folder = new File(src);
        boolean result = false;
        try {
            String childs[] = folder.list();
            if (childs == null || childs.length <= 0) {
                if (folder.delete()) {
                    result = true;
                }
            } else {
                for (int i = 0; i < childs.length; i++) {
                    String childName = childs[i];
                    String childPath = folder.getPath() + File.separator
                            + childName;
                    File file = new File(childPath);
                    if (file.exists() && file.isFile()) {
                        if (file.delete()) {
                            result = true;
                        } else {
                            result = false;
                            break;
                        }
                    } else if (file.exists() && file.isDirectory()) {
                        if (deleteFolder(childPath)) {
                            result = true;
                        } else {
                            result = false;
                            break;
                        }
                    }
                }
                folder.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public String getFileSize(File file) throws Exception {
        return null;
    }
}
