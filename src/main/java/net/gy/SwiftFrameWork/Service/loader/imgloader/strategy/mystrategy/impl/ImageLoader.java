package net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import net.gy.SwiftFrameWork.Service.loader.imgloader.IImageLoader;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.IThreadInterface;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.ImgLoadThreadCallBack;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.cache.FileCache;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.cache.MemoryCache;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.ImgLoadConfigs;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.PhotoToLoad;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.interfaceimpl.LoadThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.Future;
import android.util.DisplayMetrics;

public class ImageLoader implements IImageLoader, ImgLoadThreadCallBack {

    private static WeakHashMap<Context,WeakReference<IImageLoader>> imgloads;

    private MemoryCache memoryCache = new MemoryCache();
    public FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private Context context;
    private Handler handler;
    private ImgLoadConfigs configs;
    private IThreadInterface thread = LoadThread.getInterface();
    private Vector<Future> rlist;

    public ImageLoader(Context context, ImgLoadConfigs configs) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.configs = configs;
        handler = new MyHandler(this);
        fileCache = new FileCache(context);
        rlist = new Vector<Future>();
    }

    public ImageLoader(Context context, int imgSize) {
        // TODO Auto-generated constructor stub
        this.context = context;
        handler = new MyHandler(this);
        fileCache = new FileCache(context);
        rlist = new Vector<Future>();
    }

    public static IImageLoader getInstance(Context context,ImgLoadConfigs configs){
        if (imgloads == null)
            imgloads = new WeakHashMap<Context,WeakReference<IImageLoader>>();
        if (imgloads.get(context)!=null&&imgloads.get(context).get()!=null)
            return imgloads.get(context).get();
        IImageLoader imageLoader = new ImageLoader(context,configs);
        imgloads.put(context,new WeakReference<IImageLoader>(imageLoader));
        return imageLoader;
    }

    public static IImageLoader With(Context context){
        ImgLoadConfigs configs = new ImgLoadConfigs();
        return getInstance(context,configs);
    }


    public void ShowImg(String url, ImageView imageView) {
        imageViews.put(imageView, url);

        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(url, imageView);
            imageView.setImageResource(configs.getLoadImgsrc());
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad photoToLoad = new PhotoToLoad(url, imageView);
        Future future = thread.AddTask(thread.getLoadRunnable(handler, photoToLoad, this));
        rlist.add(future);
    }


    public boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    public static Bitmap decodeFileAuto(ImageView imageView,File file){
        return decodeSampledBitmapFromPath(null,getImageViewSize(imageView),file);
    }

    public static Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale type. It should be the power of 2.
            final int REQUIRED_SIZE = 360;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static ImageSize getImageViewSize(ImageView imageView) {
        DisplayMetrics displayMetrics = imageView.getContext().getResources()
                .getDisplayMetrics();

        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width = imageView.getWidth();
        if (width <= 0) {
            width = params.width;
        }
        if (width <= 0) {
            width = getImaeViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0) {
            height = params.height;
        }
        if (height <= 0) {
            height = getImaeViewFieldValue(imageView, "mMaxHeight");
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        ImageSize imageSize = new ImageSize();
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    public static int getImaeViewFieldValue(ImageView imageView, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static Bitmap decodeSampledBitmapFromPath(String path,
                                                 ImageSize imageSize,File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, imageSize);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        if (file == null)
            bitmap = BitmapFactory.decodeFile(path, options);
        else
            bitmap = BitmapFactory.decodeFile(file.getPath(),options);

        return bitmap;
    }

    /**
     *
     *
     * @param options
     * @param imageSize
     * @return
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, ImageSize imageSize) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;// ѹ��ȡ����

        if (width > imageSize.width || height > imageSize.height) {
            int widthRadio = Math.round(width * 1.0f / imageSize.width);
            int heightRadio = Math.round(height * 1.0f / imageSize.height);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }


    private void showTip(String tip) {
        Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<ImageLoader> mcontext;

        public MyHandler(ImageLoader context) {
            // TODO Auto-generated constructor stub
            this.mcontext = new WeakReference<ImageLoader>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int Flag = msg.what;
            switch (Flag) {
                case 0:
                    String errmsg = (String) msg.getData().getSerializable(
                            "ErroMsg");
                    ((ImageLoader) mcontext.get()).showTip(errmsg);
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }

    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    @Override
    public void threadCall(PhotoToLoad photoToLoad, Bitmap bitmap) {
        // TODO Auto-generated method stub
        ImageSize size = getImageViewSize(photoToLoad.imageView);
        Bitmap decodedBitmap = zoomImage(bitmap,size.width,size.height);
        memoryCache.put(photoToLoad.url, decodedBitmap);
        if (imageViewReused(photoToLoad))
            return;
        handler.post(new BitmapDisplayerThread(bitmap, photoToLoad));
    }

    class BitmapDisplayerThread implements Runnable {

        private Bitmap bitmap;
        private PhotoToLoad photoToLoad;

        public BitmapDisplayerThread(Bitmap bitmap, PhotoToLoad photoToLoad) {
            // TODO Auto-generated constructor stub
            this.bitmap = bitmap;
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(configs.getLoadImgsrc());
        }

    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    @Override
    public void destory(){
        for (Future future:rlist){
            if (future!=null)
                future.cancel(true);
        }
        rlist.clear();
        imgloads.remove(context);
        context = null;
    }

    @Override
    public void removeCache(String url) {
        memoryCache.remove(url);
        fileCache.remove(url);
    }


    @Override
    public ImageLoader getInstance() {
        // TODO Auto-generated method stub
        return this;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static class ImageSize {
        protected int width;
        protected int height;
    }


}
