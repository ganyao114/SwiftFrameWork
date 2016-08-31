package net.gy.SwiftFrameWork.MVVM.Impl;

import android.util.Log;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpBinderEntity;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;
import net.gy.SwiftFrameWork.MVVM.Entity.ParType;
import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;
import net.gy.SwiftFrameWork.MVVM.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.MVVM.templet.configs.HttpTheadConfigBean;
import net.gy.SwiftFrameWork.Model.entity.Entry;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

/**
 * Created by pc on 16/8/30.
 */
public class HttpTemplet extends HttpThreadTemplet implements IThreadCallback{

    private IHttpModel httpModel;
    private List<IFilter> filters;
    private Object[] pars;
    private HttpBinderEntity binderEntity;
    private String defaultUrl;
    private JsonTree jsonTree;


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

    public List<IFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<IFilter> filters) {
        this.filters = filters;
    }

    public IHttpModel getHttpModel() {
        return httpModel;
    }

    public void setHttpModel(IHttpModel httpModel) {
        this.httpModel = httpModel;
    }

    public Object[] getPars() {
        return pars;
    }

    public void setPars(Object[] pars) {
        this.pars = pars;
    }

    public HttpBinderEntity getBinderEntity() {
        return binderEntity;
    }

    public void setBinderEntity(HttpBinderEntity binderEntity) {
        this.binderEntity = binderEntity;
    }

    public JsonTree getJsonTree() {
        return jsonTree;
    }

    public void setJsonTree(JsonTree jsonTree) {
        this.jsonTree = jsonTree;
    }

    @Override
    protected void OnRun() throws Exception {
        Log.e("gy","成功!来自"+invoker.getName());
        setPar();
        Object object = httpModel.dohttp();
        if (object == null)
            throw new HttpServiceException("返回为空");
        if (filters!=null&&filters.size()!=0){
            for (IFilter filter:filters)
            {
                object = filter.filter(object);
            }
        }
        if (jsonTree != null)
            object = JsonParse.getValue(jsonTree,object.toString());
        callBack.onSuccess(invoker,object);
    }

    private void setPar() {
        if (defaultUrl!=null)
            httpModel.setUrl(defaultUrl);
        if (pars == null||pars.length == 0)
            return;
        for (int i = 0; i < pars.length; i++) {
            Entry<ParType, String> par = binderEntity.getPars()[i];
            String parvalue = pars[i].toString();
            switch (par.getKey()) {
                case URL:
                    httpModel.setUrl(parvalue);
                    break;
                case PAR:
                    httpModel.addPar(par.getValue(), parvalue);
                    break;
                case HEADER:
                    httpModel.addHeader(par.getValue(), parvalue);
                    break;
            }
        }
    }

    @Override
    public void OnStop() {
        httpModel.destory();
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
