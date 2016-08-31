package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.ParField;

/**
 * Created by pc on 16/8/30.
 */
public class HttpPostModel extends BaseHttpModel{

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
    public Object dohttp() {
        return null;
    }

}
