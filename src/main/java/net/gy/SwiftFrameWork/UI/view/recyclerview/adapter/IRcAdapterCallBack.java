package net.gy.SwiftFrameWork.UI.view.recyclerview.adapter;

import net.gy.SwiftFrameWork.UI.view.baserecycleview.ViewHolder;

/**
 * Created by gy on 2016/4/12.
 */
public interface IRcAdapterCallBack {
    public void adapterCall(ViewHolder holder, int position);
    public void adapterCall(ViewHolder holder, Object object);
}
