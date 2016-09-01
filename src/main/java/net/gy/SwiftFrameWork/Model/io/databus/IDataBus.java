package net.gy.SwiftFrameWork.Model.io.databus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import net.gy.SwiftFrameWork.Model.io.databus.entity.DBDataSrc;
import net.gy.SwiftFrameWork.Model.io.databus.entity.HttpDataSrc;

import java.util.Collection;

/**
 * Created by gy on 2016/3/16.
 * 数据总线 连接各个模块
 * 响应式
 */
public interface IDataBus {
    public IDataBus setDataSrc(Object object)throws Exception;
    public IDataBus setDataSrc(int resId)throws Exception;
    public IDataBus setDataSrc(Collection<?> list)throws Exception;
    public IDataBus setDataSrc(HttpDataSrc httpSrc)throws Exception;
    public IDataBus setDataSrc(DBDataSrc httpSrc)throws Exception;
    public IDataBus bindView(View view)throws Exception;
    public IDataBus bindView(ViewGroup view)throws Exception;
    public IDataBus bindView(AbsListView view)throws Exception;
    public IDataBus bindView(RecyclerView view)throws Exception;
    public IDataBus setControlFilter(ControlCallback filter)throws Exception;
    public IDataBus notifyView()throws Exception;
    public IDataBus setLoadedCallBack(IDataLoaded callBack)throws Exception;
    public boolean destory()throws Exception;
}
