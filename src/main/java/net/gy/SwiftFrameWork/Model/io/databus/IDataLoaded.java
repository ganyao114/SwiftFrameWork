package net.gy.SwiftFrameWork.Model.io.databus;

import android.view.View;

import net.gy.SwiftFrameWork.MVP.Model.IModelDriver;

/**
 * Created by gy on 2016/3/30.
 */
public interface IDataLoaded {
    public void onDataLoaded(IModelDriver data,View view);
    public void onDataGeted(IModelDriver data);
}
