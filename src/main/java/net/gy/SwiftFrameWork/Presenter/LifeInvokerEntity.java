package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by gy939 on 2016/9/28.
 */
public class LifeInvokerEntity {

    private Method method;
    private Class invokerType;
    private ActivityLifeType type;
    private Class<? extends Activity> tarType;

//    private ReferenceQueue q = new ReferenceQueue();
//
//    private Vector<WeakReference<Object>> invokers = new Vector<>();


    public Class<? extends Activity> getTarType() {
        return tarType;
    }

    public void setTarType(Class<? extends Activity> tarType) {
        this.tarType = tarType;
    }

    public Class getInvokerType() {
        return invokerType;
    }

    public void setInvokerType(Class invokerType) {
        this.invokerType = invokerType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ActivityLifeType getType() {
        return type;
    }

    public void setType(ActivityLifeType type) {
        this.type = type;
    }

//    private void checkQueue(){
//        Reference reference =  q.poll();
//        while (reference != null){
//            invokers.remove(reference);
//            reference = q.poll();
//        }
//    }

    public void invoke(Object invoker,Object... pars){
        try {
            method.invoke(invoker,pars);
        } catch (Exception e) {

        }
    }

}
