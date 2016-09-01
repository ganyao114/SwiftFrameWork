package net.gy.SwiftFrameWork.Model.io.databus;

import android.view.View;

import net.gy.SwiftFrameWork.MVP.Model.IModelDriver;
import net.gy.SwiftFrameWork.Service.core.IControlDriver;

import java.lang.ref.WeakReference;

/**
 * Created by gy on 2016/4/1.
 */
public interface ControlCallback extends IControlDriver<IModelDriver,WeakReference<View>[]>{
    @Override
    boolean control(IModelDriver data, WeakReference<View>[] views) throws Exception;
}
