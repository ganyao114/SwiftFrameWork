package net.gy.SwiftFrameWork.UI.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder>{
	
	private List<T> list;
	private Context mContext;

	public BaseRecycleAdapter(List<T> list,Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		mContext = context;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(BaseRecycleViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseRecycleViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
