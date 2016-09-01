package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy;

import android.graphics.Bitmap;
import android.os.Handler;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.PhotoToLoad;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;

import java.io.File;
import java.util.concurrent.Future;


/**
 * Created by gy on 2015/11/7.
 */
public interface IThreadInterface {
    public Future AddTask(Runnable runnable);

    public Runnable getLoadRunnable(Handler handler, PhotoToLoad photoToLoad, ImgLoadThreadCallBack context);

    public Bitmap getBitmap(String url, File f) throws Exception;

    public Bitmap getBitmap(String url, File f, ImageLoader.ImageSize size) throws Exception;
}
