package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.entity.Invoker;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by pc on 16/5/14.
 */
public class ObserverProxy<T> implements OnObserver<T>,IObserverProxy<T>{

    private Vector<OnObserver<T>> observers;
    private Invoker invoker;

    public ObserverProxy() {
        observers = new Vector<OnObserver<T>>();
        invoker = Invoker.getInstance();
    }

    @Override
    public void onSuccess(T t) {
        String methodName = "onSuccess";
        for (OnObserver<T> observer:observers){
            try {
                Method method = observer.getClass().getMethod(methodName,Object.class);
                invoker.invoke(method,observer,t);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        String methodName = "onError";
        Class type = Throwable.class;
        for (OnObserver<T> observer:observers){
            try {
                Method method = observer.getClass().getMethod(methodName,type);
                invoker.invoke(method,observer,throwable);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinished() {
        String methodName = "onFinished";
        for (OnObserver<T> observer:observers){
            try {
                Method method = observer.getClass().getMethod(methodName);
                invoker.invoke(method,observer,null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addObserver(OnObserver<T> observer) {
        observers.add(observer);
    }

    @Override
    public void rmObserver(OnObserver<T> observer) {
        observers.remove(observer);
    }

    @Override
    public void clear() {
        observers.clear();
    }

    @Override
    public Vector<OnObserver<T>> getObservers() {
        return observers;
    }
}
