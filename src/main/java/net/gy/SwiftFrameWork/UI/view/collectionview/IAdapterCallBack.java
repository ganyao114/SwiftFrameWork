package net.gy.SwiftFrameWork.UI.view.collectionview;

import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;

/**
 * Created by gy on 2015/11/13.
 */
public interface IAdapterCallBack {
    public void adapterCall(IViewHolder holder, int position);
}
