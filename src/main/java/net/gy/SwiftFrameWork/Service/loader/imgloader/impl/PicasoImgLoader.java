package net.gy.SwiftFrameWork.Service.loader.imgloader.impl;

import android.content.Context;
import android.widget.ImageView;

import net.gy.SwiftFrameWork.Service.loader.imgloader.control.ImgLoaderControl;
import com.squareup.picasso.Picasso;

/**
 * Created by gy on 2016/1/28.
 */
public class PicasoImgLoader extends ImgLoaderControl{

    private Picasso picasso;

    public PicasoImgLoader(Context context) {
        super(null);
        picasso = Picasso.with(context);
    }

    @Override
    public void ShowImg(String src, ImageView view) {
        picasso.load(src).into(view);
    }

}
