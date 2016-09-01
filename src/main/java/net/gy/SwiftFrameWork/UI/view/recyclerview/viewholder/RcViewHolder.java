package net.gy.SwiftFrameWork.UI.view.recyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by gy on 2016/4/11.
 */
public class RcViewHolder extends RecyclerView.ViewHolder implements IRcViewHolder {
    private SparseArray<View> mViews;
    private static int mPosition;
    public RcViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public <T extends View> T getView(int ViewId) {
        T view = (T) mViews.get(ViewId);
        if (view == null) {
            view = (T) itemView.findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return view;
    }

}
