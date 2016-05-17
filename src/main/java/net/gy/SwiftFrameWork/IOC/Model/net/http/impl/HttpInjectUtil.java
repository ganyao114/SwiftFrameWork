package net.gy.SwiftFrameWork.IOC.Model.net.http.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.Model.net.http.annotation.InjectHttp;
import net.gy.SwiftFrameWork.IOC.Model.net.http.annotation.InjectHttpParser;
import net.gy.SwiftFrameWork.IOC.Model.net.http.entity.HttpInjectBean;
import net.gy.SwiftFrameWork.IOC.Service.thread.handler.BaseHandler;
import net.gy.SwiftFrameWork.IOC.Service.thread.handler.IHandler;
import net.gy.SwiftFrameWork.Model.net.http.IHttpDealCallBack;
import net.gy.SwiftFrameWork.Model.net.http.IUploadCallBack;
import net.gy.SwiftFrameWork.Model.net.http.impl.MyHttpService;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;
import net.gy.SwiftFrameWork.Service.thread.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by gy on 2015/11/25.
 */
public final class HttpInjectUtil {

    private static HttpInjectUtil injectUtil;

    private Map<Class<?>,Map<String, HttpInjectBean>> injectBeanMap;

    private Class<?> Dailinter = IHttpDealCallBack.class;

    private String Proxymethod = "dealReturn";

    private String handlerProxyMethod = "handlePost";

    private Class<?> handlerinter = IHandler.class;

    public static HttpInjectUtil getInstance() {
        synchronized (HttpInjectUtil.class) {
            if (injectUtil == null)
                injectUtil = new HttpInjectUtil();
        }
        return injectUtil;
    }

    private HttpInjectUtil() {
        injectBeanMap = new WeakHashMap<Class<?>,Map<String, HttpInjectBean>>();
    }

    public void inject(Object object) {
        if (injectBeanMap.containsKey(object.getClass()))
            return;
        injectBeanMap.put(object.getClass(),new HashMap<String, HttpInjectBean>());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity")
                    || template.getName().equals("android.support.v4.app.FragmentActivity")
                    || template.getName().equals("android.support.v4.app.Fragment")
                    || template.getName().equals("android.app.Fragment")) {
                break;
            }
            injectCallBack(template,object);
            injectDailMethod(template,object);
            template = template.getSuperclass();
        }

    }


    private void injectDailMethod(Class<?> clazz, Object object){
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            InjectHttpParser methodinject = method.getAnnotation(InjectHttpParser.class);
            if (methodinject != null)
                doInjectDailMethod(method, object,methodinject);
        }
    }

    private void doInjectDailMethod(Method method, Object object, InjectHttpParser methodinject) {
        String key = methodinject.value();
        if (!injectBeanMap.get(object.getClass()).containsKey(key))
            return;
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(Proxymethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(Dailinter.getClassLoader(), new Class<?>[]{Dailinter}, handler);
        MyHttpService service = injectBeanMap.get(object.getClass())
                .get(key)
                .getService();
        service.setDealCallBack((IHttpDealCallBack) bussnessproxy);
    }

    private void injectCallBack(Class<?> clazz, Object object) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            InjectHttp methodinject = method.getAnnotation(InjectHttp.class);
            if (methodinject != null) {
                switch (methodinject.connectmode()){
                    case Get:
                        doInjectPost(method, object, methodinject);
                        break;
                    case Post:
                        doInjectPost(method, object, methodinject);
                        break;
                    case UpLoad:
                        doInjectUpload(method, object, methodinject);
                        break;
                    case UrlConnection:
                        break;
                    case Download:
                        break;
                }

            }
        }
    }

    private void doInjectUpload(Method method, Object object, InjectHttp methodinject) {
        String key = method.getName();
        if (injectBeanMap.get(object.getClass()).containsKey(key))
            return;
        final HttpInjectBean injectBean = new HttpInjectBean();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(handlerProxyMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(handlerinter.getClassLoader(), new Class<?>[]{handlerinter}, handler);
        BaseHandler baseHandler = new BaseHandler((IHandler)bussnessproxy);
        final MyHttpService service = new MyHttpService();
        service.setUrl(methodinject.url());
        HttpThreadTemplet httpThreadTemplet = new HttpThreadTemplet(baseHandler,service) {
            @Override
            protected void OnRun() throws Exception {
                service.getUploadCallBack().onStart();
                Serializable result = httpService.upload();
                injectBean.setResult(result);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putSerializable("result",result);
                msg.what = 1;
                msg.setData(data);
                handler.sendMessage(msg);
            }

            @Override
            protected HttpTheadConfigBean SetConfig() {
                HttpTheadConfigBean configBean = new HttpTheadConfigBean
                        (false, 0, "连接超时", "加载超时","加载错误");
                return configBean;
            }

            @Override
            public Serializable dealReturn(String result) {
                return null;
            }

            @Override
            protected void onFinally() {
                service.getUploadCallBack().onComplete();
            }
        };
        IUploadCallBack uploadCallBack = null;
        try {
            uploadCallBack = methodinject.uploadCall().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        service.setUploadCallBack(uploadCallBack);
        injectBean.setService(service);
        injectBean.setUrl(methodinject.url());
        injectBean.setConnectMode(methodinject.connectmode());
        injectBean.setRunMode(methodinject.mode());
        injectBean.setHttpThreadTemplet(httpThreadTemplet);
        injectBean.setUploadViewCall(methodinject.uploadCall());
        injectBeanMap.get(object.getClass()).put(key,injectBean);
    }

    private void doInjectPost(Method method, Object object, InjectHttp methodinject) {
        String key = method.getName();
        if (injectBeanMap.get(object.getClass()).containsKey(key))
            return;
        final HttpInjectBean injectBean = new HttpInjectBean();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(handlerProxyMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(handlerinter.getClassLoader(), new Class<?>[]{handlerinter}, handler);
        BaseHandler baseHandler = new BaseHandler((IHandler)bussnessproxy);
        MyHttpService service = new MyHttpService();
        service.setUrl(methodinject.url());
        HttpThreadTemplet httpThreadTemplet = new HttpThreadTemplet(baseHandler,service) {
            @Override
            protected void OnRun() throws Exception {
                Serializable result = httpService.getDataPost();
                injectBean.setResult(result);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putSerializable("result",result);
                msg.what = 1;
                msg.setData(data);
                handler.sendMessage(msg);
            }

            @Override
            protected HttpTheadConfigBean SetConfig() {
                HttpTheadConfigBean configBean = new HttpTheadConfigBean
                        (false, 0, "连接超时", "加载超时","加载错误");
                return configBean;
            }

            @Override
            public Serializable dealReturn(String result) {
                return null;
            }
        };
        injectBean.setService(service);
        injectBean.setUrl(methodinject.url());
        injectBean.setConnectMode(methodinject.connectmode());
        injectBean.setRunMode(methodinject.mode());
        injectBean.setHttpThreadTemplet(httpThreadTemplet);
        injectBeanMap.get(object.getClass()).put(key,injectBean);
    }

    public void post(Object object,String key,Map<String,String> params,Map<String,String> headers) {
        if (injectBeanMap.get(object.getClass()) == null)
            return;
        HttpInjectBean injectBean = injectBeanMap.get(object.getClass()).get(key);
        injectBean.getService().setParams(params);
        injectBean.getService().setHeaders(headers);
        MyWorkThreadQueue.AddTask(injectBean.getHttpThreadTemplet());
    }

    public void upload(Object object, String key, File file, Context context, Map<String, String> params, Map<String, String> headers){
        if (injectBeanMap.get(object.getClass()) == null)
            return;
        HttpInjectBean injectBean = injectBeanMap.get(object.getClass())
                .get(key);
        injectBean.getService()
                .getUploadCallBack()
                .setup(context);
        injectBean.getService().setParams(params);
        injectBean.getService().setHeaders(headers);
        MyWorkThreadQueue.AddTask(injectBean.getHttpThreadTemplet());
    }

    public void remove(Object object) {
        injectBeanMap.remove(object.getClass());
        System.gc();
    }

    public Serializable getResult(Message message){
        return message.getData().getSerializable("result");
    }

    public ExtCall with(Object object,String key){
        return new ExtCall(object, key);
    }


    public static void Inject(Object object){
        getInstance().inject(object);
    }

    public static void Remove(Object object){
        getInstance().remove(object);
    }

    public static Serializable GetResult(Message message){
        return getInstance().getResult(message);
    }

    public static ExtCall With(Object object,String key){
        return getInstance().with(object, key);
    }

    public static void doHttp(Object object,String key,Map<String,String> params,Map<String,String> headers){
        getInstance().post(object,key,params,headers);
    }

    public static void Upload(Object object,String key,File file, Context context,Map<String,String> params,Map<String,String> headers){
        getInstance().upload(object,key,file,context,params,headers);
    }

    public class ExtCall{
        Object object;
        String key;
        Map<String,String> params;
        Map<String,String> headers;
        Context context;
        public ExtCall(Object object, String key) {
            this.object = object;
            this.key = key;
        }
        public ExtCall setHeaders(Map<String,String> headers){
            this.headers = headers;
            return this;
        }
        public ExtCall addHeader(String key,String value){
            if (headers == null)
                headers = new HashMap<String,String>();
            headers.put(key,value);
            return this;
        }
        public ExtCall setParams(Map<String,String> params){
            this.params = params;
            return this;
        }
        public ExtCall addParams(String key,String value){
            if (params == null)
                params = new HashMap<String,String>();
            params.put(key,value);
            return this;
        }
        public ExtCall doHttp(){
            getInstance().post(object, key, params, headers);
            return this;
        }
        public ExtCall setContext(Context context){
            this.context = context;
            return this;
        }
        public ExtCall upload(File file){
            getInstance().upload(object,key,file,context,params,headers);
            return this;
        }
    }

}
