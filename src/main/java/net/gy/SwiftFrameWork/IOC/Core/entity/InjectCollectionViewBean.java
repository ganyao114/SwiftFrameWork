package net.gy.SwiftFrameWork.IOC.Core.entity;

import android.widget.GridView;
import android.widget.ListView;

import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;

import java.util.List;

/**
 * Created by gy on 2015/12/7.
 */
public class InjectCollectionViewBean {
    private ListView listView;
    private GridView gridView;
    private NomListAdapter adapter;
    private List list;
    private Object Proxyobject;
    private int itemlayout;

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public NomListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(NomListAdapter adapter) {
        this.adapter = adapter;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Object getProxyobject() {
        return Proxyobject;
    }

    public void setProxyobject(Object proxyobject) {
        Proxyobject = proxyobject;
    }

    public int getItemlayout() {
        return itemlayout;
    }

    public void setItemlayout(int itemlayout) {
        this.itemlayout = itemlayout;
    }
}
