package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;

import java.lang.reflect.Method;

/**
 * Created by pc on 16/5/15.
 */
public class OnPublisherProxy<T> implements OnPublisher<T>{

    private OnPublisher<T> onPublisher;
    private Invoker invoker;

    public OnPublisherProxy(OnPublisher<T> onPublisher) {
        this.onPublisher = onPublisher;
    }

    @Override
    public void call(OnObserver<T> observer) {
        String MethodName = "call";
        Class parType = OnObserver.class;
        if (invoker == null)
            invoker = Invoker.getInstance();
        try {
            Method method = onPublisher.getClass().getMethod(MethodName,parType);
            invoker.invoke(method,onPublisher, observer);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
