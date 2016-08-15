package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.entity;

import android.util.SparseArray;
import android.view.View;

import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.impl.ListBinder;
import net.gy.SwiftFrameWork.R;
import net.gy.SwiftFrameWork.UI.view.baserecycleview.ViewHolder;
import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by gy on 2016/3/15.
 */
public class BinderPackage {
    private SparseArray<BinderTarget> bindlist;
    private List list;

    public BinderPackage() {
        bindlist = new SparseArray<BinderTarget>();
    }

    public BinderPackage(SparseArray<BinderTarget> bindlist, List list) {
        this.bindlist = bindlist;
        this.list = list;
    }


    private void doBind(IViewHolder holder, int position,int key,BinderTarget bt){
        View view = holder.getView(key);
        Field field = bt.getField();
        Object value = null;
        if (field != null) {
            try {
                value = field.get(list.get(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null)
                return;
        }
        Method method = bt.getMethod();
        Method ltnmethod = bt.getLtnMethod();
        Object ltnImpl = bt.getLtnImpl();
        try {
            if (method != null)
                method.invoke(view,value);
            if (ltnmethod != null) {
                ltnmethod.invoke(view, ltnImpl);
                view.setTag(position);
                view.setTag(R.id.EntityTag,list.get(position));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void doBind(ViewHolder holder, int position,int key,BinderTarget bt){
        View view = holder.getView(key);
        Field field = bt.getField();
        Object value = null;
        if (field != null) {
            try {
                value = field.get(list.get(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null)
                return;
        }
        Method method = bt.getMethod();
        Method ltnmethod = bt.getLtnMethod();
        Object ltnImpl = bt.getLtnImpl();
        try {
            if (method != null)
                method.invoke(view,value);
            if (ltnmethod != null) {
                ltnmethod.invoke(view, ltnImpl);
                view.setTag(position);
                view.setTag(R.id.EntityTag,list.get(position));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setHolder(IViewHolder holder, int position){
        for (int i = 0;i < bindlist.size();i++){
            int key = bindlist.keyAt(i);
            BinderTarget bt = bindlist.get(key);
            doBind(holder,position,key,bt);
            if (bt.list != null){
                for (BinderTarget binderTarget:bt.list)
                    doBind(holder,position,key,binderTarget);
            }
        }
    }

    public void setHolder(ViewHolder holder, int position){
        for (int i = 0;i < bindlist.size();i++){
            int key = bindlist.keyAt(i);
            BinderTarget bt = bindlist.get(key);
            doBind(holder,position,key,bt);
            if (bt.list != null){
                for (BinderTarget binderTarget:bt.list)
                    doBind(holder,position,key,binderTarget);
            }
        }
    }

    public SparseArray<BinderTarget> getBindlist() {
        return bindlist;
    }

    public void setBindlist(SparseArray<BinderTarget> bindlist) {
        this.bindlist = bindlist;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public class BinderTarget{

        private Class type;
        private Method method;
        private Field field;

        private Object ltnImpl;
        private Method ltnMethod;
        private Class ltnType;

        public List<BinderTarget> list;

        public BinderTarget(Class type, Field field, Method method) {
            this.type = type;
            this.field = field;
            this.method = method;
        }

        public BinderTarget(Object ltnImpl, Method ltnMethod, Class ltnType) {
            this.ltnImpl = ltnImpl;
            this.ltnMethod = ltnMethod;
            this.ltnType = ltnType;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Object getLtnImpl() {
            return ltnImpl;
        }

        public void setLtnImpl(Object ltnImpl) {
            this.ltnImpl = ltnImpl;
        }

        public Method getLtnMethod() {
            return ltnMethod;
        }

        public void setLtnMethod(Method ltnMethod) {
            this.ltnMethod = ltnMethod;
        }

        public Class getLtnType() {
            return ltnType;
        }

        public void setLtnType(Class ltnType) {
            this.ltnType = ltnType;
        }
    }

}
