package net.gy.SwiftFrameWork.IOC.UI.view.listinject.impl;

import android.app.Activity;
import android.widget.ListView;

import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.UI.view.listinject.annotation.InjectListSrc;
import net.gy.SwiftFrameWork.IOC.UI.view.listinject.annotation.InjectListview;
import net.gy.SwiftFrameWork.IOC.UI.view.listinject.entity.InjectListBean;
import net.gy.SwiftFrameWork.UI.view.collectionview.IAdapterCallBack;
import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.CommonAdapter;
import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2015/11/29.
 */
public class ListInjectUtil {

    private static ListInjectUtil injectUtil;

    private Map<Class<?>,Map<String, InjectListBean>> injectlist;

    private Class<?> callInter = IAdapterCallBack.class;

    private String callMethod = "adapterCall";

    private String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static ListInjectUtil getInstance(){
        synchronized (ListInjectUtil.class) {
            if (injectUtil == null)
                injectUtil = new ListInjectUtil();
        }
        return injectUtil;
    }

    private ListInjectUtil() {
        this.injectlist = new HashMap<Class<?>,Map<String, InjectListBean>>();
    }

    public void inject(Activity object){
        if (!injectlist.containsKey(object.getClass()))
            injectlist.put(object.getClass(),new HashMap<String, InjectListBean>());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity") || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            //injectList(template,object);
            injectAdapterCall(template,object);

            template = template.getSuperclass();
        }
    }

    private void injectList(Class<?> template, Activity object) {
        Field[] fields = template.getDeclaredFields();
        for (Field field : fields) {
            InjectListSrc listinject = field.getAnnotation(InjectListSrc.class);
            if (listinject != null)
                doInjectList(field, object, listinject);
        }
    }

    private void doInjectList(Field field, Activity object, InjectListSrc listinject) {
        String key = listinject.value();
        if (injectlist.get(object.getClass()).containsKey(key))
            return;
        InjectListBean injectListBean = new InjectListBean();
        field.setAccessible(true);
        //injectListBean.setList(field.get());
    }

    private void injectAdapterCall(Class<?> template, Activity object) {
        Method[] methods = template.getDeclaredMethods();
        for (Method method : methods) {
            InjectListview methodinject = method.getAnnotation(InjectListview.class);
            if (methodinject != null)
                doInjectAdapterCall(method, object,methodinject);
        }
    }

    private void doInjectAdapterCall(Method method, Activity object, InjectListview methodinject) {
        String key = method.getName();
        if (injectlist.get(object.getClass()).containsKey(key))
            return;
        InjectListBean injectListBean = new InjectListBean();
        int listlayout = methodinject.listlayout();
        int itemlayout = methodinject.itemlayout();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(callMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(callInter.getClassLoader(), new Class<?>[]{callInter}, handler);
        ListView listView = (ListView) object.findViewById(listlayout);
        injectListBean.setProxyobject(bussnessproxy);
        injectListBean.setListView(listView);
        injectListBean.setItemlayout(itemlayout);
        injectlist.get(object.getClass()).put(key,injectListBean);
    }

    public void show(Activity object,String key,List list){
        InjectListBean injectListBean = injectlist.get(object.getClass()).get(key);
        injectListBean.setList(list);
        NomListAdapter adapter = new NomListAdapter(object,list,injectListBean.getItemlayout(),(IAdapterCallBack)injectListBean.getProxyobject());
        injectListBean.setAdapter(adapter);
        injectListBean.getListView().setAdapter(adapter);
    }

    public void refresh(Activity object,String key){
        InjectListBean injectListBean = injectlist.get(object.getClass()).get(key);
        injectListBean.getAdapter().notifyDataSetChanged();
    }

    public void remove(Activity object){
        injectlist.remove(object.getClass());
    }

    public ListView getListView(Activity object,String key) {
        return injectlist.get(object.getClass()).get(key).getListView();
    }

    public CommonAdapter getAdapter(Activity object,String key){
        return injectlist.get(object.getClass()).get(key).getAdapter();
    }

}
