package net.gy.SwiftFrameWork.Model.net.http;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;

/**
 * Created by gy on 2015/11/6.
 */
public interface IHttpService {
    public Serializable getDataPost() throws Exception;
    public Bitmap getBitmap(String url, File f) throws Exception;
    public Serializable getDataGet()throws Exception;
    public void download()throws Exception;
    public String upload(File file)throws Exception;
    public String upload()throws Exception;
}
