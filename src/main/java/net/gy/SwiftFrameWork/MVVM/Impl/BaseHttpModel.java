package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.ParField;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;

import java.util.Map;

/**
 * Http请求基类
 * Created by pc on 16/8/30.
 */
public abstract class BaseHttpModel implements IHttpModel{

    protected final static ThreadLocal<ParField> parLocal = new ThreadLocal<ParField>();

    public BaseHttpModel() {
    }

    @Override
    public void setPars(Map pars) {
        if (parLocal.get() == null)
            parLocal.set(new ParField());
        parLocal.get().setPars(pars);
    }

    @Override
    public void setHeaders(Map headers) {
        if (parLocal.get() == null)
            parLocal.set(new ParField());
        parLocal.get().setHeaders(headers);
    }

    @Override
    public void setUrl(String url) {
        if (parLocal.get() == null)
            parLocal.set(new ParField());
        parLocal.get().setUrl(url);
    }

    @Override
    public void addPar(String key, String value) {
        if (parLocal.get() == null)
            parLocal.set(new ParField());
        parLocal.get().addPar(key, value);
    }

    @Override
    public void addHeader(String key, String value) {
        if (parLocal.get() == null)
            parLocal.set(new ParField());
        parLocal.get().addHeader(key, value);
    }

    @Override
    public void destory() {
        parLocal.remove();
    }
}
