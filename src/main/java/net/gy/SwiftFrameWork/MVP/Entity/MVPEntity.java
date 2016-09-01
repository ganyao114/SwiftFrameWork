package net.gy.SwiftFrameWork.MVP.Entity;

import android.util.SparseArray;
import android.view.View;

import net.gy.SwiftFrameWork.MVP.Presenter.IPresenterCallBack;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by gy on 2016/3/13.
 */
public class MVPEntity {
    private SparseArray<WeakReference<View>> mViews;
    private Map<Class,Object> listeners;
    private WeakReference<IPresenterCallBack> callbackRef;

    public SparseArray<WeakReference<View>> getmViews() {
        return mViews;
    }

    public void setmViews(SparseArray<WeakReference<View>> mViews) {
        this.mViews = mViews;
    }

    public Map<Class, Object> getListeners() {
        return listeners;
    }

    public void setListeners(Map<Class, Object> listeners) {
        this.listeners = listeners;
    }

    public WeakReference<IPresenterCallBack> getCallbackRef() {
        return callbackRef;
    }

    public void setCallbackRef(WeakReference<IPresenterCallBack> callbackRef) {
        this.callbackRef = callbackRef;
    }
}
