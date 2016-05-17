package net.gy.SwiftFrameWork.UI.view.collectionview.adapter;


import android.content.Context;

import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;

import java.util.List;

/**
 * Created by gy on 2016/4/12.
 */
public abstract class MutipartListAdapter<T> extends CommonAdapter{

    public MutipartListAdapter(Context context, List list, int layoutid) {
        super(context, list, layoutid);
    }

    @Override
    protected void getMyview(IViewHolder holder, int position) {
        convert(holder, (T) list.get(position));
    }

    protected abstract void convert(IViewHolder holder, T t);
}
