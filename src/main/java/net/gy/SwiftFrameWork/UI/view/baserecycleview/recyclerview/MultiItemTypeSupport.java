package net.gy.SwiftFrameWork.UI.view.baserecycleview.recyclerview;

public interface MultiItemTypeSupport<T>
{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}