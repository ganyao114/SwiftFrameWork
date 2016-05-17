package net.gy.SwiftFrameWork.UI.view.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.gy.SwiftFrameWork.UI.view.baserecycleview.ViewHolder;
import net.gy.SwiftFrameWork.UI.view.baserecycleview.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by gy on 2016/4/12.
 */
public class NomRcViewAdapter<T> extends CommonAdapter {

    private IRcAdapterCallBack callBack;

    public NomRcViewAdapter(Context context, int layoutId, List datas,IRcAdapterCallBack callBack) {
        super(context, layoutId, datas);
        this.callBack = callBack;
    }


    @Override
    public void convert(ViewHolder holder, Object object) {
        callBack.adapterCall(holder,object);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        callBack.adapterCall((ViewHolder) holder,position);
    }
}
