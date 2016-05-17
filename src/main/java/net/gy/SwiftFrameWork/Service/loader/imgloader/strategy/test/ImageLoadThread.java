package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.test;

import android.graphics.Bitmap;
import android.os.Handler;

import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.PhotoToLoad;
import net.gy.SwiftFrameWork.Service.thread.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

import java.io.File;
import java.io.Serializable;


public class ImageLoadThread extends HttpThreadTemplet {
	
	private PhotoToLoad photoToLoad;
	private ImgLoadThreadCallBack imgReusedCallBack;
	
	public ImageLoadThread(Handler handler,PhotoToLoad photoToLoad,ImgLoadThreadCallBack imgReusedCallBack) {
		super(handler);
		// TODO Auto-generated constructor stub
		this.photoToLoad = photoToLoad;
		this.imgReusedCallBack = imgReusedCallBack;
	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
		
		if(imgReusedCallBack.getInstance().imageViewReused(photoToLoad))
			return;
		File file = imgReusedCallBack.getInstance().
				fileCache.getFile(photoToLoad.url);
		Bitmap bitmap = ImageLoader
				.decodeFile(file);
		if(bitmap != null){
			imgReusedCallBack.threadCall(photoToLoad, bitmap);
		}
		else{
			bitmap = httpService.getBitmap(photoToLoad.url,file);
			imgReusedCallBack.threadCall(photoToLoad, bitmap);
		}
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, "���ӳ�ʱ", "���س�ʱ","���ش���");
		return configBean; 
	}

	@Override
	public Serializable dealReturn(String result) {
		return null;
	}
}
