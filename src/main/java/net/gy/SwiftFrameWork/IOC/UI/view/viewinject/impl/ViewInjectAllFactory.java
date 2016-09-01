package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import net.gy.SwiftFrameWork.IOC.Core.annotation.InjectCollectionView;
import net.gy.SwiftFrameWork.IOC.Core.entity.CollectionViewType;
import net.gy.SwiftFrameWork.IOC.Core.entity.InjectCollectionViewBean;
import net.gy.SwiftFrameWork.IOC.Core.entity.InjectEntity;
import net.gy.SwiftFrameWork.IOC.Core.impl.InjectBase;
import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.ContentView;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.EventBase;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.ViewInject;
import net.gy.SwiftFrameWork.UI.view.collectionview.IAdapterCallBack;
import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gy on 2015/12/7.
 */
public class ViewInjectAllFactory extends InjectBase {

    private static ViewInjectAllFactory viewInjectAll;

    private Class<?> callInter = IAdapterCallBack.class;

    private String callMethod = "adapterCall";


    private ViewInjectAllFactory getInstance(){
        synchronized (ViewInjectAllFactory.class) {
            if (viewInjectAll == null)
                viewInjectAll = new ViewInjectAllFactory();
        }
        return viewInjectAll;
    }

    public void inject(Object object, View view,LayoutInflater inflater){
        if (!injectMap.containsKey(object.getClass()))
            injectMap.put(object.getClass(),new InjectEntity());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity")
                    || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            injectClass(template,object,view,inflater);
            template = template.getSuperclass();
        }
    }

    @Override
    protected void injectCall(Class<?> template, Object object) {
        injectClass(template,object,null,null);
        injectField(template,object);
        injectMethod(template,object);
    }

    private void injectClass(Class<?> template, Object object, View view, LayoutInflater inflater) {
        ContentView contentView = template.getAnnotation(ContentView.class);
        if (contentView != null){
            Class<?> clazz = getKind(object);
            if (clazz.equals(Activity.class)){
                WeakReference<Activity> activity = new WeakReference<Activity>((Activity) object);
                get(object).setActivityRefrence(activity);
                (activity.get()).setContentView(contentView.value());
            }else if (clazz.equals(Fragment.class)){
                WeakReference<Fragment> fragment = new WeakReference<Fragment>((Fragment) object);
                get(object).setFragmentRefrence(fragment);
                view = inflater.inflate(contentView.value(),null);
                WeakReference<View> viewWeakReference = new WeakReference<View>(view);
                get(object).setViewRefrence(viewWeakReference);
            }else if(clazz.equals(android.support.v4.app.Fragment.class)){

            }
        }
    }

    private void injectMethod(Class<?> template, Object object) {
        Method[] methods = template.getDeclaredMethods();
        for (Method method:methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (annotations == null)
                return;
            for (Annotation annotation:annotations){
                Class<? extends Annotation> annotationType = annotation
                        .annotationType();
                //拿到注解上的注解
                EventBase eventBaseAnnotation = annotationType
                        .getAnnotation(EventBase.class);
                if (eventBaseAnnotation!=null){
                    doInjectEvents(object,eventBaseAnnotation,
                            annotation,method,annotationType);
                }else if (annotation.annotationType().equals(InjectCollectionView.class)){
                    doInjectCollection(object,method);
                }
            }
        }
    }

    private void doInjectCollection(Object object, Method method) {
        if (get(object).getCollections() == null)
            get(object).setCollections(new HashMap<String, InjectCollectionViewBean>());
        String key = method.getName();
        InjectCollectionView injectAnnotation = method.getAnnotation(InjectCollectionView.class);
        CollectionViewType ViewType = injectAnnotation.type();
        int listlayout = injectAnnotation.listlayout();
        int itemlayout = injectAnnotation.itemlayout();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(callMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(callInter.getClassLoader(), new Class<?>[]{callInter}, handler);
        InjectCollectionViewBean injectListBean = new InjectCollectionViewBean();
        injectListBean.setProxyobject(bussnessproxy);
        injectListBean.setItemlayout(itemlayout);
        if (ViewType == CollectionViewType.ListView){
            ListView listView = null;
            if (get(object).getActivityRefrence()!=null)
                listView = (ListView) (get(object).getActivityRefrence().get()).findViewById(listlayout);
            else if (get(object).getFragmentRefrence()!=null)
                listView = (ListView) (get(object).getViewRefrence().get()).findViewById(listlayout);
            injectListBean.setListView(listView);
        }else {
            GridView gridview = null;
            if (get(object).getActivityRefrence()!=null)
                gridview = (GridView) (get(object).getActivityRefrence().get()).findViewById(listlayout);
            else if (get(object).getFragmentRefrence()!=null)
                gridview = (GridView) (get(object).getViewRefrence().get()).findViewById(listlayout);
            injectListBean.setGridView(gridview);
        }
        get(object).getCollections().put(key,injectListBean);
    }

    private void doInjectEvents(Object object, EventBase eventBaseAnnotation, Annotation annotation,
                                Method method, Class<? extends Annotation> annotationType) {
        String listenerSetter = eventBaseAnnotation
                .listenerSetter();
        Class<?> listenerType = eventBaseAnnotation.listenerType();
        String methodName = eventBaseAnnotation.methodName();

        try {
            Method aMethod = annotationType
                    .getDeclaredMethod("type");
            int[] viewIds = (int[]) aMethod
                    .invoke(annotation);
            DynamicHandler handler = new DynamicHandler(object);
            handler.addMethod(methodName, method);
            Object listener = Proxy.newProxyInstance(
                    listenerType.getClassLoader(),
                    new Class<?>[]{listenerType}, handler);
            for (int viewId : viewIds) {
                View view = null;
                if (get(object).getActivityRefrence()!=null)
                    view = (get(object).getActivityRefrence().get()).findViewById(viewId);
                else if (get(object).getFragmentRefrence()!=null)
                    view = (get(object).getViewRefrence().get()).findViewById(viewId);
                Method setEventListenerMethod = view.getClass()
                        .getMethod(listenerSetter, listenerType);
                setEventListenerMethod.invoke(view, listener);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void injectField(Class<?> template, Object object) {
        Field[] fields = template.getDeclaredFields();
        for (Field field:fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations == null)
                return;
            for (Annotation annotation:annotations){
                if (annotation.annotationType().equals(ViewInject.class)){
                    doViewInject(field,object);
                }else{

                }
            }
        }
    }

    private void doViewInject(Field field, Object object) {
        ViewInject viewInject = field.getAnnotation(ViewInject.class);
        if (get(object).getActivityRefrence()!=null){
            Object resView = (get(object).getActivityRefrence().get()).findViewById(viewInject.value());
            field.setAccessible(true);
            try {
                field.set(object, resView);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else if(get(object).getFragmentRefrence()!=null){
            Object resView = (get(object).getViewRefrence().get()).findViewById(viewInject.value());
            field.setAccessible(true);
            try {
                field.set(object, resView);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {

        }
    }

    public void showList(Object object,String key,List list){
        if (get(object) == null)
            return;
        InjectCollectionViewBean injectListBean = get(object).getCollections().get(key);
        injectListBean.setList(list);
        NomListAdapter adapter = new NomListAdapter((Context) object,list,injectListBean.getItemlayout(),(IAdapterCallBack)injectListBean.getProxyobject());
        injectListBean.setAdapter(adapter);
        if (injectListBean.getListView()!=null)
            injectListBean.getListView().setAdapter(adapter);
        else if (injectListBean.getGridView()!=null)
            injectListBean.getGridView().setAdapter(adapter);
    }

    public void refreshList(Object object,String key){
        if (get(object) == null)
            return;
        InjectCollectionViewBean injectListBean = get(object).getCollections().get(key);
        injectListBean.getAdapter().notifyDataSetChanged();
    }

    public NomListAdapter getAdapter(Object object, String key){
        return get(object).getCollections().get(key).getAdapter();
    }

    public ListView getListView(Object object,String key) {
        return get(object).getCollections().get(key).getListView();
    }

    public GridView getGridView(Object object,String key){
        return get(object).getCollections().get(key).getGridView();
    }

    public static ViewInjectInner with(Object object){
        ViewInjectInner inner = new ViewInjectInner();
        return inner;
    }

    public static class ViewInjectInner{

    }

}

