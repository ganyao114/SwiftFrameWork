package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;
import net.gy.SwiftFrameWork.MVVM.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.MVVM.templet.configs.HttpTheadConfigBean;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/30.
 */
public class HttpTemplet extends HttpThreadTemplet implements IThreadCallback{

    private IHttpModel httpModel;
    private IFilter[] filters;


    public HttpTemplet(ICallBackInner callBack, IHttpModel httpModel, Method invoker) {
        super(callBack,invoker);
        this.httpModel = httpModel;
        this.threadCallback = this;
    }

    public HttpTemplet(ICallBackInner callBack, HttpTheadConfigBean configBean,IHttpModel httpModel,Method invoker) {
        super(callBack, configBean,invoker);
        this.httpModel = httpModel;
        this.threadCallback = this;
    }

    public IFilter[] getFilters() {
        return filters;
    }

    public void setFilters(IFilter[] filters) {
        this.filters = filters;
    }

    public IHttpModel getHttpModel() {
        return httpModel;
    }

    public void setHttpModel(IHttpModel httpModel) {
        this.httpModel = httpModel;
    }

    @Override
    protected void OnRun() throws Exception {
        Object object = httpModel.dohttp();
        if (object == null)
            throw new HttpServiceException("返回为空");
        if (filters!=null&&filters.length!=0){
            for (IFilter filter:filters)
            {
                object = filter.filter(object);
            }
        }
        callBack.onSuccess(invoker,object);
    }

    @Override
    public void OnStop() {
        httpModel.destory();
    }
}
