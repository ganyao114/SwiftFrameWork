package net.gy.SwiftFrameWork.UI.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseRecycleViewHolder extends RecyclerView.ViewHolder{
	
	private SparseArray<View> mViews;
    private static int mPosition;
    private View mconvertView;

	public BaseRecycleViewHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
	}

	
}
