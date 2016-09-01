package net.gy.SwiftFrameWork.Model.io.databus.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import net.gy.SwiftFrameWork.MVP.Model.IModelDriver;
import net.gy.SwiftFrameWork.Model.io.databus.ControlCallback;
import net.gy.SwiftFrameWork.Model.io.databus.IDataBus;
import net.gy.SwiftFrameWork.Model.io.databus.IDataLoaded;
import net.gy.SwiftFrameWork.Model.io.databus.entity.DBDataSrc;
import net.gy.SwiftFrameWork.Model.io.databus.entity.HttpDataSrc;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2016/3/30.
 */
public class DefaultDataBus implements IDataBus{

    private static Map<String,IDataBus> dataEntitys = new HashMap<String, IDataBus>();

    private String name;
    private IModelDriver data;
    private List<WeakReference<View>> viewbinds;
    private List<ControlCallback> filters;


    public static IDataBus getInstance(String name){
        if (dataEntitys.containsKey(name))
            return dataEntitys.get(name);
        return new DefaultDataBus(name);
    }

    public static IDataBus getInstance(){
        return new DefaultDataBus();
    }

    private DefaultDataBus(String name){
        this.name = name;
        dataEntitys.put(name,this);
        viewbinds = new ArrayList<WeakReference<View>>();
        filters = new ArrayList<ControlCallback>();
    }

    private DefaultDataBus(){
        viewbinds = new ArrayList<WeakReference<View>>();
        filters = new ArrayList<ControlCallback>();
    }

    @Override
    public IDataBus setDataSrc(Object object) throws Exception {
        return this;
    }

    @Override
    public IDataBus setDataSrc(int resId) throws Exception {
        return this;
    }

    @Override
    public IDataBus setDataSrc(Collection<?> list) throws Exception {
        return this;
    }

    @Override
    public IDataBus setDataSrc(HttpDataSrc httpSrc) throws Exception {
        return this;
    }

    @Override
    public IDataBus setDataSrc(DBDataSrc httpSrc) throws Exception {
        return this;
    }

    @Override
    public IDataBus bindView(View view) throws Exception {
        return this;
    }

    @Override
    public IDataBus bindView(ViewGroup view) throws Exception {
        return this;
    }

    @Override
    public IDataBus bindView(AbsListView view) throws Exception {
        return this;
    }

    @Override
    public IDataBus bindView(RecyclerView view) throws Exception {
        return this;
    }

    @Override
    public IDataBus setControlFilter(ControlCallback filter) throws Exception {
        filters.add(filter);
        return this;
    }

    @Override
    public IDataBus notifyView() throws Exception {
        for (ControlCallback filter:filters)
            filter.control(data, (WeakReference<View>[]) viewbinds.toArray());
        return this;
    }

    @Override
    public IDataBus setLoadedCallBack(IDataLoaded callBack) throws Exception {

        return this;
    }

    @Override
    public boolean destory() throws Exception {
        dataEntitys.remove(name);
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
