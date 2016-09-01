package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.interfaceimpl;

import android.graphics.Bitmap;
import android.os.Handler;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.IThreadInterface;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.ImgLoadThreadCallBack;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.PhotoToLoad;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;
import net.gy.SwiftFrameWork.Service.thread.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

import java.io.File;
import java.io.Serializable;


public class ImageLoadThread extends HttpThreadTemplet {

    private PhotoToLoad photoToLoad;
    private ImgLoadThreadCallBack imgReusedCallBack;
    private IThreadInterface iThreadInterface;

    public ImageLoadThread(Handler handler, PhotoToLoad photoToLoad, ImgLoadThreadCallBack imgReusedCallBack,
                           IThreadInterface iThreadInterface) {
        super(handler);
        // TODO Auto-generated constructor stub
        this.photoToLoad = photoToLoad;
        this.imgReusedCallBack = imgReusedCallBack;
        this.iThreadInterface = iThreadInterface;
    }

    @Override
    protected void OnRun() throws Exception {
        // TODO Auto-generated method stub

        if (imgReusedCallBack.getInstance().imageViewReused(photoToLoad))
            return;
        File file = imgReusedCallBack.getInstance().
                fileCache.getFile(photoToLoad.url);
        Bitmap bitmap = ImageLoader
                .decodeFileAuto(photoToLoad.imageView,file);
        if (bitmap != null) {
            imgReusedCallBack.threadCall(photoToLoad, bitmap);
        } else {
            bitmap = iThreadInterface.getBitmap(photoToLoad.url, file);
            imgReusedCallBack.threadCall(photoToLoad, bitmap);
        }
    }

    @Override
    protected HttpTheadConfigBean SetConfig() {
        // TODO Auto-generated method stub
        HttpTheadConfigBean configBean = new HttpTheadConfigBean
                (false, 0, "连接超时", "加载超时","加载错误");
        return configBean;
    }

    @Override
    public Serializable dealReturn(String result) {
        return null;
    }
}
