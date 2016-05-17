package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy;

import android.graphics.Bitmap;
import android.os.Handler;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.PhotoToLoad;

import java.io.File;


/**
 * Created by gy on 2015/11/7.
 */
public interface IThreadInterface {
    public void AddTask(Runnable runnable);

    public Runnable getLoadRunnable(Handler handler, PhotoToLoad photoToLoad, ImgLoadThreadCallBack context);

    public Bitmap getBitmap(String url, File f) throws Exception;
}
