package net.gy.SwiftFrameWork.UI.view.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.gy.SwiftFrameWork.UI.view.recyclerview.viewholder.RcViewHolder;

/**
 * Created by gy on 2016/4/11.
 */
public class BaseRecycleViewAdapter extends RecyclerView.Adapter<RcViewHolder> {
    @Override
    public RcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RcViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
