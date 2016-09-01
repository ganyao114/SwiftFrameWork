package net.gy.SwiftFrameWork.IOC.UI.view.listinject.entity;

import android.widget.ListView;

import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;

import java.util.List;

/**
 * Created by gy on 2015/11/29.
 */
public class InjectListBean {
    private ListView listView;
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
