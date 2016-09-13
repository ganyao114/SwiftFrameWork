package net.gy.SwiftFrameWork.MVVM.Entity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Cache.MethodCache;
import net.gy.SwiftFrameWork.MVVM.Cache.MvvmCache;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpGetModel;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpPostModel;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpTemplet;
import net.gy.SwiftFrameWork.MVVM.Impl.ViewDisplayer;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;
import net.gy.SwiftFrameWork.MVVM.Interface.IMethodProxy;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/29.
 */
public final class HttpMethodProxy implements IMethodProxy,ICallBackInner {

    public final static String METHOD_RET = "dohasret";
    public final static String METHOD_NORET = "donoret";

    private Map<Method,MethodCache> methodCaches;
    private Map<Method,HttpTemplet> httpTemplets = new HashMap<>();
    private Map<String,ICallBack> callBacks;
    private Handler mainHandler;
    private Map<Method,Object> reses = new HashMap<>();
    private Map<String,WeakReference<View>> viewContentRef;

    private WeakReference<View> viewsingle;
    private ICallBack sigleCallback;

    public HttpMethodProxy(Map<Method,MethodCache> methodCaches,Map<String,ICallBack> callBacks) {
        this.methodCaches = methodCaches;
        this.callBacks = callBacks;
        init();
    }

    private final void init() {
        mainHandler = new Handler(Looper.getMainLooper());
        for (Map.Entry<Method,MethodCache> binderEntityEntry:methodCaches.entrySet()) {
            HttpBinderEntity binderEntity = binderEntityEntry.getValue().getBinderEntity();
            Method invoker = binderEntityEntry.getKey();
            HttpSrcMethod control = binderEntity.getControl();
            IHttpModel httpModel = null;
            switch (control.connMode()) {
                case Post:
                    httpModel = new HttpPostModel();
                    break;
                case Get:
                    httpModel = new HttpGetModel();
                    break;
            }

            Class<? extends IFilter>[] filters = control.filters();
            List<IFilter> filtersImpls = null;
            if (filters.length > 0&&filters[0]!=IFilter.class){
                filtersImpls = new ArrayList<>();
                for (Class<? extends IFilter> ifilter:filters) {
                    IFilter filterImpl = MvvmCache.getFilter(ifilter);
                    filtersImpls.add(filterImpl);
                }
            }


            HttpTemplet httpTemplet = new HttpTemplet(this, httpModel,invoker);

            String url = control.url();
            String urlBase = BaseUrlFactory.getUrl(control.baseUrlKey());
            if (urlBase != null)
                url = urlBase + url;
            if (!url.equals(""))
                httpTemplet.setDefaultUrl(url);

            httpTemplet.setFilters(filtersImpls);

            httpTemplet.setBinderEntity(binderEntity);

            httpTemplet.setJsonTree(binderEntityEntry.getValue().getRet().getJsonTree());

            httpTemplets.put(invoker,httpTemplet);
        }

    }

    @Override
    public void donoret(Method invoker, Object[] pars) {
        HttpBinderEntity binderEntity = methodCaches.get(invoker).getBinderEntity();
        HttpTemplet httpTemplet = httpTemplets.get(invoker);
        IHttpModel httpModel = httpTemplet.getHttpModel();
        if (pars.length != binderEntity.getPars().length)
            throw new IllegalArgumentException("输入参数不匹配");
        //参数初始化
        httpTemplet.setPars(pars);
        if (binderEntity.getControl().runMode() == HttpRunMode.Sync){
            httpTemplet.run();
            return;
        }else{
            //提交线程池处理
            MyWorkThreadQueue.AddTask(httpTemplet);
        }
        return;
    }



    @Override
    public Object dohasret(Method invoker,Object[] pars) {
        HttpBinderEntity binderEntity = methodCaches.get(invoker).getBinderEntity();
        HttpTemplet httpTemplet = httpTemplets.get(invoker);
        IHttpModel httpModel = httpTemplet.getHttpModel();
        if (pars.length != binderEntity.getPars().length)
            throw new IllegalArgumentException("输入参数不匹配");
        //参数初始化
        httpTemplet.setPars(pars);

        if (binderEntity.getControl().runMode() == HttpRunMode.Sync){
            httpTemplet.run();
            return reses.get(invoker);
        }else{
            //提交线程池处理
            MyWorkThreadQueue.AddTask(httpTemplet);
        }
        return null;
    }

    @Override
    public void onSuccess(Method invoker,final Object o) {
        HttpBinderEntity binderEntity = methodCaches.get(invoker).getBinderEntity();
        final ICallBack callBack;
        if (sigleCallback == null)
            callBack = callBacks.get(invoker.getName());
        else
            callBack = sigleCallback;
        if (binderEntity.getControl().runMode() == HttpRunMode.Sync){
            reses.put(invoker,o);
            if (callBack != null)
                callBack.onSuccess(o);
            show(invoker,o);
        }else {
            if (callBack != null)
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });
            show(invoker,o);
        }
    }

    private void show(final Method invoker, final Object o) {
        final View content;
        if (viewsingle == null&&(viewContentRef==null||viewContentRef.size()==0))
            return;
        if (viewsingle == null) {
            content = viewContentRef.get(invoker.getName()).get();
        }
        else
            content = viewsingle.get();
        if (content == null)
            return;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    ViewDisplayer.show(methodCaches.get(invoker).getRet().getViewBindControl().getRoot(),o,content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onFailed(Method invoker,final Object o) {
        HttpBinderEntity binderEntity = methodCaches.get(invoker).getBinderEntity();
        final ICallBack callBack;
        if (sigleCallback == null)
            callBack = callBacks.get(invoker.getName());
        else
            callBack = sigleCallback;
        if (binderEntity.getControl().runMode() == HttpRunMode.Sync) {
            if (callBack != null)
                callBack.onFailed(o);
        }else {
            if (callBack != null)
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(o);
                    }
                });
        }
    }

    public void setViewContentRef(Map<String, WeakReference<View>> viewContentRef) {
        this.viewContentRef = viewContentRef;
    }

    public void setViewsingle(WeakReference<View> viewsingle) {
        this.viewsingle = viewsingle;
    }

    public void setSigleCallback(ICallBack sigleCallback) {
        this.sigleCallback = sigleCallback;
    }
}
