package net.gy.SwiftFrameWork.UI.view.collectionview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;
import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.impl.BaseViewHolder;

import java.util.List;

/**
 * Created by gy on 2015/11/6.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private int layoutid;
    protected List<T> list;
    protected Context mContext;

    public CommonAdapter(Context context, List<T> list, int layoutid) {
        this.mContext = context;
        this.list = list;
        this.layoutid = layoutid;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder holder = BaseViewHolder.getInstance(mContext, layoutid, convertView, parent, position);
        getMyview(holder, position);
        return holder.getConvertview();
    }

    protected abstract void getMyview(IViewHolder holder, int position);
}
