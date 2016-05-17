package net.gy.SwiftFrameWork.UI.view.collectionview;

import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;

import java.util.List;

/**
 * Created by gy on 2015/11/13.
 */
public interface IBaseCollectionView<T> {
    public void init(List<T> list, IAdapterCallBack callBack, int layoutid);

    public NomListAdapter<T> getAdapter();

    public void show(List<T> list);

    public void show();

    public void refresh();
}
