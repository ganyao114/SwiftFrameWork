package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpBinderEntity;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;
import net.gy.SwiftFrameWork.MVVM.Entity.ParType;
import net.gy.SwiftFrameWork.MVVM.Entity.SessionFactory;
import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;
import net.gy.SwiftFrameWork.MVVM.Interface.IHandler;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpDriver;
import net.gy.SwiftFrameWork.MVVM.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.MVVM.templet.configs.HttpTheadConfigBean;
import net.gy.SwiftFrameWork.Model.entity.Entry;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Http线程体实现
 * Created by pc on 16/8/30.
 */
public class HttpTemplet extends HttpThreadTemplet implements IThreadCallback{

    private IHttpDriver httpModel;
    private List<IFilter> filters;
    private Object[] pars;
    private HttpBinderEntity binderEntity;
    private String defaultUrl;
    private JsonTree jsonTree;


    public HttpTemplet(ICallBackInner callBack, IHttpDriver httpModel, Method invoker) {
        super(callBack,invoker);
        this.httpModel = httpModel;
        this.threadCallback = this;
    }

    private void sessionInit() {
        //初始化session
        String sessionkey = binderEntity.getControl().session();
        if (sessionkey == null||sessionkey.equals(""))
            return;
        SessionFactory.Session session = SessionFactory.getSession(sessionkey);
        if (session!=null){
            httpModel.setHeaders(session.getHeaders());
            httpModel.setPars(session.getPars());
        }
    }

    public HttpTemplet(ICallBackInner callBack, HttpTheadConfigBean configBean, IHttpDriver httpModel, Method invoker) {
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

    public IHttpDriver getHttpModel() {
        return httpModel;
    }

    public void setHttpModel(IHttpDriver httpModel) {
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
        sessionInit();
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
        String obj = String.valueOf(object);
        if (jsonTree != null)
            object = JsonParse.getValue(jsonTree,object.toString());
        if (object instanceof IHandler)
            ((IHandler)object).handler(obj);
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
