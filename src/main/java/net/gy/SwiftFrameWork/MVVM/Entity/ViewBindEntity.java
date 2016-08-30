package net.gy.SwiftFrameWork.MVVM.Entity;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBindEntity {

    private @IdRes int[] viewids;
    private Class<? extends View> viewtype;
    private Method bindMethod;
    private Field valuefield;
    private Class valuetype;
    private ViewType viewType;

    public Class<? extends View> getViewtype() {
        return viewtype;
    }

    public void setViewtype(Class<? extends View> viewtype) {
        this.viewtype = viewtype;
    }

    public Method getBindMethod() {
        return bindMethod;
    }

    public void setBindMethod(Method bindMethod) {
        this.bindMethod = bindMethod;
    }

    public Field getValuefield() {
        return valuefield;
    }

    public void setValuefield(Field valuefield) {
        this.valuefield = valuefield;
    }


    public Class getValuetype() {
        return valuetype;
    }

    public void setValuetype(Class valuetype) {
        this.valuetype = valuetype;
    }

    public int[] getViewids() {
        return viewids;
    }

    public void setViewids(int[] viewids) {
        this.viewids = viewids;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }
}
