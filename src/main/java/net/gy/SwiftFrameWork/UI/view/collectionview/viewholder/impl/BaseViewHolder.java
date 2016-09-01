package net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.gy.SwiftFrameWork.Service.loader.imgloader.IImageLoader;
import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;

/**
 * Created by gy on 2015/11/6.
 */
public class BaseViewHolder implements IViewHolder {
    private SparseArray<View> mViews;
    private static int mPosition;
    private View mconvertView;

    public static BaseViewHolder getInstance(Context context, int parentviewsrc, View convertView, ViewGroup parent, int position) {
        BaseViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new BaseViewHolder(context, parentviewsrc, convertView, parent, position);
        } else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }
        mPosition = position;
        return viewHolder;
    }

    public BaseViewHolder(Context context, int parentviewsrc, View convertView, ViewGroup parent, int position) {
        mPosition = position;
        mViews = new SparseArray<View>();
        mconvertView = LayoutInflater.from(context).inflate(parentviewsrc, parent, false);
        mconvertView.setTag(this);
    }


    @Override
    public <T extends View> T getView(int ViewId) {
        T view = (T) mViews.get(ViewId);
        if (view == null) {
            view = (T) mconvertView.findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return view;
    }

    @Override
    public View getConvertview() {
        return mconvertView;
    }

    @Override
    public IViewHolder setText(int Rsc, String text) {
        TextView view = (TextView) getView(Rsc);
        view.setText(text);
        return this;
    }

    @Override
    public IViewHolder setImgRec(int Rsc, int imgrsc) {
        ImageView imageView = (ImageView) getView(Rsc);
        imageView.setImageResource(imgrsc);
        return this;
    }

    @Override
    public IViewHolder setImgBitmap(int Rsc, Bitmap bitmap) {
        ImageView imageView = (ImageView) getView(Rsc);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public IViewHolder ShowImg(int Rsc, String url, IImageLoader loader) {
        ImageView imageView = (ImageView) getView(Rsc);
        loader.ShowImg(url, imageView);
        return this;
    }
}
