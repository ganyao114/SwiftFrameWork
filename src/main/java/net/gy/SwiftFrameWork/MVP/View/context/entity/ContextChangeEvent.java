package net.gy.SwiftFrameWork.MVP.View.context.entity;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by gy on 2016/1/30.
 */
public class ContextChangeEvent {

    public final static int ACTIVITY_ACTIVITY = 0;
    public final static int SERVICE_SERVICE = 1;
    public final static int ACTIVITY_SERVICE = 2;
    public final static int SERVICE_ACTIVITY = 3;

    private int action;
    private WeakReference<Context> contextRef;

    public ContextChangeEvent() {
    }

    public ContextChangeEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Context getContext(){
        return contextRef.get();
    }

    public void setContext(Context context){
        contextRef = new WeakReference<Context>(context);
    }
}
