package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.impl;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import net.gy.SwiftFrameWork.IOC.Core.cache.FieldEntity;
import net.gy.SwiftFrameWork.IOC.Core.cache.MethodEntity;
import net.gy.SwiftFrameWork.IOC.Core.cache.ReflectWithCache;
import net.gy.SwiftFrameWork.IOC.Core.entity.ClassMemberPackage;
import net.gy.SwiftFrameWork.IOC.Core.parase.ClassMemberParase;
import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation.ListBinderBase;
import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation.ListBinderLtnBase;
import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation.ListDataSrc;
import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.entity.BinderPackage;
import net.gy.SwiftFrameWork.R;
import net.gy.SwiftFrameWork.UI.view.baserecycleview.ViewHolder;
import net.gy.SwiftFrameWork.UI.view.collectionview.IAdapterCallBack;
import net.gy.SwiftFrameWork.UI.view.collectionview.adapter.NomListAdapter;
import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;
import net.gy.SwiftFrameWork.UI.view.recyclerview.adapter.IRcAdapterCallBack;
import net.gy.SwiftFrameWork.UI.view.recyclerview.adapter.NomRcViewAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2016/3/15.
 */
public class ListBinder {

    private static ListBinder utils;


    public static ListBinder getInstance(){
        synchronized (ListBinder.class){
            if (utils == null)
                utils = new ListBinder();
        }
        return utils;
    }

    public ExtCall with(AbsListView view){
        return new ExtCall(view);
    }

    public ExtCall with(RecyclerView view){
        return new ExtCall(view);
    }

    public static ExtCall With(AbsListView view){
        return getInstance().with(view);
    }

    public static ExtCall With(RecyclerView view){
        return getInstance().with(view);
    }

    public void bind(AbsListView view,RecyclerView recyclerView,List<?> list,Class clazzType,Object ltnImpl){
        if (list == null)
            return;
        Class clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;

        ListDataSrc listDataSrc = (ListDataSrc) clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
//        Map<Class<? extends Annotation>,Annotation> map = ReflectWithCache.getClassAnnoWithType(clazz);
//        if (map == null)
//            return;
//        Annotation annotation = map.get(ListDataSrc.class);
//        if (annotation == null)
//            return;
//        ListDataSrc listDataSrc = (ListDataSrc) annotation;
//        List<ClassMemberPackage> mems = null;
//        try {
//            mems = ClassMemberParase.GetFileds(list.get(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (mems == null||mems.size()==0)
//            return;
        FieldEntity[] fieldEntities = ReflectWithCache.getFieldsWithType(clazz);
        final BinderPackage binderPackage = new BinderPackage();
        binderPackage.setList(list);
        for (FieldEntity entity : fieldEntities){
            Map<Class<? extends Annotation>,Annotation> annotations = entity.getAnnotations();
            for (Map.Entry<Class<? extends Annotation>,Annotation> entry: annotations.entrySet()){
                Annotation anno = entry.getValue();
                Class<? extends Annotation> annoType = entry.getKey();
                ListBinderBase annobase = annoType
                        .getAnnotation(ListBinderBase.class);
                ListBinderLtnBase ltnannobase = annoType
                        .getAnnotation(ListBinderLtnBase.class);
                if (annobase!=null){
                    Method method = null;
                    try {
                        method = annobase.viewType().getDeclaredMethod(annobase.methodName(),annobase.dataType());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (method != null) {

                        for (int item : getItems(anno)) {
                            if (binderPackage.getBindlist().get(item) == null) {
                                BinderPackage.BinderTarget target =
                                        binderPackage.new BinderTarget(annobase.viewType(), entity.getField(), method);
                                binderPackage.getBindlist().put(item, target);
                            }
                            else {
                                BinderPackage.BinderTarget target = binderPackage.getBindlist().get(item);
                                target.setType(annobase.viewType());
                                target.setMethod(method);
                                target.setField(entity.getField());
                            }
                        }
                    }
                }
                if (ltnannobase!=null){
                    Method method = null;
                    try {
                        method = ltnannobase.viewType().getDeclaredMethod(ltnannobase.listenerSetter(),ltnannobase.listenerType());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (method==null)
                        continue;
                    Object ltn = ltnImpl;
                    BinderPackage.BinderTarget target =
                            binderPackage.new BinderTarget(ltn,method,ltnannobase.listenerType());
                    for (int item:getItems(anno)){
                        if (binderPackage.getBindlist().get(item)==null){
                            binderPackage.getBindlist().put(item,target);
                        }else {
                            BinderPackage.BinderTarget tar = binderPackage.getBindlist().get(item);
                            if (tar.getLtnMethod()==null){
                                tar.setLtnImpl(ltn);
                                tar.setLtnMethod(method);
                                tar.setLtnType(ltnannobase.listenerType());
                            }else {
                                if (tar.list == null)
                                    tar.list = new ArrayList<BinderPackage.BinderTarget>();
                                tar.list.add(target);
                            }
                        }
                    }
                }
            }
        }
        if (view != null) {
            IAdapterCallBack callBack = new IAdapterCallBack() {
                @Override
                public void adapterCall(IViewHolder holder, int position) {
                    binderPackage.setHolder(holder, position);
                }
            };
            NomListAdapter adapter = new NomListAdapter(view.getContext(), list, listDataSrc.value(), callBack);
            adapter.setBinder(binderPackage);
            view.setAdapter(adapter);
        }
        if (recyclerView !=null){
            IRcAdapterCallBack rcAdapterCallBack = new IRcAdapterCallBack() {
                @Override
                public void adapterCall(ViewHolder holder, int position) {
                    binderPackage.setHolder(holder,position);
                }

                @Override
                public void adapterCall(ViewHolder holder, Object object) {

                }
            };
            NomRcViewAdapter adapter = new NomRcViewAdapter(recyclerView.getContext(),listDataSrc.value(),list,rcAdapterCallBack);
            recyclerView.setAdapter(adapter);
        }
    }

    public void bind(AbsListView view,List<?> list,IAdapterCallBack callBack,Class clazzType){
        if (list == null)
            return;
        Class<?> clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;
        ListDataSrc listDataSrc = clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
        NomListAdapter adapter = new NomListAdapter(view.getContext(),list,listDataSrc.value(),callBack);
        view.setAdapter(adapter);
    }

    public void bind(RecyclerView view, List<?> list, Class clazzType, Object ltnImpl){

    }

    public void bind(RecyclerView view, List<?> list, IRcAdapterCallBack callBack, Class clazzType){
        if (list == null)
            return;
        Class<?> clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;
        ListDataSrc listDataSrc = clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
        NomRcViewAdapter adapter = new NomRcViewAdapter(view.getContext(),listDataSrc.value(),list,callBack);
        view.setAdapter(adapter);
    }

    private int[] getItems(Annotation annotation){
        Class annoClazz = annotation.getClass();
        Method method = null;
        int[] items = null;
        try {
            method = annoClazz.getDeclaredMethod("value");
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            items = (int[]) method.invoke(annotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void refresh(AbsListView view){
        ((BaseAdapter)view.getAdapter()).notifyDataSetChanged();
    }

    public void refresh(RecyclerView view){
        ((RecyclerView.Adapter)view.getAdapter()).notifyDataSetChanged();
    }


    public class ExtCall{
        private AbsListView listView;
        private RecyclerView recyclerView;
        private Class clazz;
        private IAdapterCallBack callBack;
        private IRcAdapterCallBack rcCallBack;
        private Object ltnImpl;
        public ExtCall(AbsListView listView) {
            this.listView = listView;
        }

        public ExtCall(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }
        public ExtCall setClass(Class clazz){
            this.clazz = clazz;
            return this;
        }
        public ExtCall setCallBack(IAdapterCallBack callBack){
            this.callBack = callBack;
            return this;
        }

        public ExtCall setCallBack(IRcAdapterCallBack callBack){
            this.rcCallBack = callBack;
            return this;
        }

        public ExtCall setLtnImpl(Object ltnImpl) {
            this.ltnImpl = ltnImpl;
            return this;
        }

        public void bind(List<?> list){

            if (listView!=null){
                if (callBack == null)
                    getInstance().bind(listView,recyclerView,list,clazz,ltnImpl);
                else
                    getInstance().bind(listView,list,callBack,clazz);
            }

            if (recyclerView!=null){
                if (rcCallBack == null)
                    getInstance().bind(listView,recyclerView,list,clazz,ltnImpl);
                else
                    getInstance().bind(recyclerView,list,rcCallBack,clazz);
            }


        }
        public void refresh(){
            if (listView!=null)
                getInstance().refresh(listView);
            if (recyclerView!=null)
                getInstance().refresh(recyclerView);
        }
    }

}
