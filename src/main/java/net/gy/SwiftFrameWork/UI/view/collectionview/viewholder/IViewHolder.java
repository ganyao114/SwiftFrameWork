package net.gy.SwiftFrameWork.UI.view.collectionview.viewholder;

import android.graphics.Bitmap;
import android.view.View;

import net.gy.SwiftFrameWork.Service.loader.imgloader.IImageLoader;

/**
 * Created by gy on 2015/11/6.
 */
public interface IViewHolder {
    public <T extends View> T getView(int ViewId);

    public View getConvertview();

    public IViewHolder setText(int Rsc, String text);

    public IViewHolder setImgRec(int Rsc, int imgrsc);

    public IViewHolder setImgBitmap(int Rsc, Bitmap bitmap);

    public IViewHolder ShowImg(int Rsc, String url, IImageLoader loader);
}
