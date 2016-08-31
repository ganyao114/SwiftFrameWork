package net.gy.SwiftFrameWork.MVVM.Entity;

import android.os.Handler;
import android.os.Looper;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpPostModel;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpTemplet;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;
import net.gy.SwiftFrameWork.MVVM.Interface.IMethodProxy;
import net.gy.SwiftFrameWork.Model.entity.Entry;
import net.gy.SwiftFrameWork.Model.net.http.IHttpDealCallBack;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 16/8/29.
 */
public final class HttpMethodProxy implements IMethodProxy,ICallBackInner {

    public final static String METHOD_RET = "dohasret";
    public final static String METHOD_NORET = "donoret";

    private Map<Method,HttpBinderEntity> binderEntitys;
    private Map<Method,HttpTemplet> httpTemplets = new HashMap<>();
    private Map<String,ICallBack> callBacks;
    private Handler mainHandler;
    private Map<Method,Object> reses = new HashMap<>();

    public HttpMethodProxy(Map<Method,HttpBinderEntity> binderEntitys,Map<String,ICallBack> callBacks) {
        this.binderEntitys = binderEntitys;
        this.callBacks = callBacks;
        init();
    }

    private void init() {
        mainHandler = new Handler(Looper.getMainLooper());
        for (Map.Entry<Method,HttpBinderEntity> binderEntityEntry:binderEntitys.entrySet()) {
            HttpBinderEntity binderEntity = binderEntityEntry.getValue();
            Method invoker = binderEntityEntry.getKey();
            HttpSrcMethod control = binderEntity.getControl();
            IHttpModel httpModel = null;
            switch (control.connMode()) {
                case Post:
                    httpModel = new HttpPostModel();
                    break;
                case Get:
                    break;
            }
            String url = control.url();
            if (!url.equals(""))
                httpModel.setUrl(url);

            HttpTemplet httpTemplet = new HttpTemplet(this, httpModel,invoker);

            httpTemplets.put(invoker,httpTemplet);
        }

    }

    @Override
    public void donoret(Method invoker, Object[] pars) {
        HttpBinderEntity binderEntity = binderEntitys.get(invoker);
        HttpTemplet httpTemplet = httpTemplets.get(invoker);
        IHttpModel httpModel = httpTemplet.getHttpModel();
        if (pars.length != binderEntity.getPars().length)
            throw new IllegalArgumentException("输入参数不匹配");
        //参数初始化
        for (int i = 0;i < pars.length ; i++){
            Entry<ParType,String> par = binderEntity.getPars()[i];
            String parvalue = pars[i].toString();
            switch (par.getKey()){
                case URL:
                    httpModel.setUrl(parvalue);
                    break;
                case PAR:
                    httpModel.addPar(par.getValue(),parvalue);
                    break;
                case HEADER:
                    httpModel.addHeader(par.getValue(),parvalue);
                    break;
            }
        }
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
        HttpBinderEntity binderEntity = binderEntitys.get(invoker);
        HttpTemplet httpTemplet = httpTemplets.get(invoker);
        IHttpModel httpModel = httpTemplet.getHttpModel();
        if (pars.length != binderEntity.getPars().length)
            throw new IllegalArgumentException("输入参数不匹配");
        //参数初始化
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
        HttpBinderEntity binderEntity = binderEntitys.get(invoker);
        final ICallBack callBack = callBacks.get(invoker.getName());
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

    private void show(Method invoker,Object o) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
            }
        });
    }


    @Override
    public void onFailed(Method invoker,final Object o) {
        HttpBinderEntity binderEntity = binderEntitys.get(invoker);
        final ICallBack callBack = callBacks.get(invoker.getName());
        if (binderEntity.getControl().runMode() == HttpRunMode.Sync) {
            if (callBack != null)
                callBack.onSuccess(o);
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
}
