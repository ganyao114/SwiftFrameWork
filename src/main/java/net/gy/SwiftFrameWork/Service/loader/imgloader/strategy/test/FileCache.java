package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.test;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileCache {
	
	private File cacheDir;
	
	public FileCache(Context context) {
		// TODO Auto-generated constructor stub
		establishCache(context);
	}
	
	private boolean establishCache(Context context)
	{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory(),"booklist");
		}else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		if (!cacheDir.exists())
			return false;
		return true;
	}
	
	public File getFile(String url)
	{
		String FileName = String.valueOf(url.hashCode());
		//String filename = URLEncoder.encode(url);  
		File file = new File(cacheDir, FileName);
		return file;
	}
	
	public void clear()
	{
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for(File f:files)
			f.delete();
	}
	
}
