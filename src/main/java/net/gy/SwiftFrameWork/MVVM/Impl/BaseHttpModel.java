package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.ParField;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;

/**
 * Created by pc on 16/8/30.
 */
public abstract class BaseHttpModel implements IHttpModel{

    protected final static ThreadLocal<ParField> parLocal = new ThreadLocal<ParField>();

    public BaseHttpModel() {
    }

    @Override
    public void destory() {
        parLocal.remove();
    }
}
