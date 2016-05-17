package net.gy.SwiftFrameWork.Model.io.databus.control;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import net.gy.SwiftFrameWork.Model.io.databus.ControlCallback;
import net.gy.SwiftFrameWork.Model.io.databus.IDataBus;
import net.gy.SwiftFrameWork.Model.io.databus.IDataLoaded;
import net.gy.SwiftFrameWork.Model.io.databus.entity.DBDataSrc;
import net.gy.SwiftFrameWork.Model.io.databus.entity.HttpDataSrc;

import java.util.Collection;

/**
 * Created by gy on 2016/3/30.
 */
public class DataBusControl implements IDataBus{

    private IDataBus proxy;

    public DataBusControl(IDataBus proxy) {
        this.proxy = proxy;
    }

    @Override
    public IDataBus setDataSrc(Object object) throws Exception {
        return null;
    }

    @Override
    public IDataBus setDataSrc(int resId) throws Exception {
        return null;
    }

    @Override
    public IDataBus setDataSrc(Collection<?> list) throws Exception {
        return null;
    }

    @Override
    public IDataBus setDataSrc(HttpDataSrc httpSrc) throws Exception {
        return null;
    }

    @Override
    public IDataBus setDataSrc(DBDataSrc httpSrc) throws Exception {
        return null;
    }

    @Override
    public IDataBus bindView(View view) throws Exception {
        return null;
    }

    @Override
    public IDataBus bindView(ViewGroup view) throws Exception {
        return null;
    }

    @Override
    public IDataBus bindView(AbsListView view) throws Exception {
        return null;
    }

    @Override
    public IDataBus bindView(RecyclerView view) throws Exception {
        return null;
    }

    @Override
    public IDataBus setControlFilter(ControlCallback filter) throws Exception {
        return null;
    }

    @Override
    public IDataBus notifyView() throws Exception {
        return null;
    }

    @Override
    public IDataBus setLoadedCallBack(IDataLoaded callBack) throws Exception {
        return null;
    }

    @Override
    public boolean destory() throws Exception {
        return false;
    }
}
