package net.gy.SwiftFrameWork.UI.customwidget.autoloadimgview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.gy.SwiftFrameWork.R;
import net.gy.SwiftFrameWork.Service.loader.imgloader.IImageLoader;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.ImgLoadConfigs;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by gy on 2015/12/6.
 */
public class AutoLoadImgView extends ImageView{

    private IImageLoader imageLoader;
    private String url;
    private ImgLoadConfigs config;
    private boolean isAnimate = true;
    private static WeakReference<Animation> animation;

    public AutoLoadImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config = new ImgLoadConfigs();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoLoadImgView, defStyleAttr, 0);
        int loadsrc = a.getResourceId(R.styleable.AutoLoadImgView_loadimgsrc,R.drawable.loading);
        config.setLoadImgsrc(loadsrc);
        url = a.getString(R.styleable.AutoLoadImgView_defaulturl);
        imageLoader = ImageLoader.getInstance(context,config);
        imageLoader.ShowImg(url,this);
        //Picasso.with(getContext()).load(url).into(this);
    }

    public AutoLoadImgView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoLoadImgView(Context context) {
        this(context,null);
    }

    private void initAnimate(){
        if (animation == null||animation.get() == null){
            synchronized (AsyncLoadImgView.class){
                if (animation == null||animation.get() == null){
                    animation = new WeakReference<Animation>(AnimationUtils.loadAnimation(getContext(), R.anim.scale));
                }
            }
        }
    }

    public void ShowImg(String url){
        imageLoader.ShowImg(url,this);
        //Picasso.with(getContext()).load(url).into(this);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (isAnimate)
            clearAnimation();
        super.setImageBitmap(bm);
        if (isAnimate){
            initAnimate();
            setAnimation(animation.get());
        }
    }

    public void setAnimate(boolean animate) {
        isAnimate = animate;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//
//    {
//
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//
//        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
//
//        setMeasuredDimension(width, height);
//
//    }

}
