package net.gy.SwiftFrameWork.Reactive.test;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.impl.Observer;

import java.util.Vector;

/**
 * Created by pc on 16/5/18.
 */
public class Publisher<T> {

    private OnPublisher<T> onPublisher;
    private Vector<OnObserver<T>> onObservers;

    public static  <T> Publisher<T> getInstance(){
        return new Publisher<T>();
    }

    private Publisher() {
        onObservers = new Vector<OnObserver<T>>();
    }

    public Publisher<T> create(OnPublisher<T> publisher){
        onPublisher = publisher;
        return this;
    }
    public Publisher<T> bind(OnObserver<T> observer){
        onObservers.add(observer);
        return this;
    }
    public Publisher<T> post(){
        onPublisher.call(new Observer<T>() {
            @Override
            public void onSuccess(T t) {
                for (OnObserver<T> observer:onObservers)
                    observer.onSuccess(t);
            }

            @Override
            public void onError(Throwable throwable) {
                for (OnObserver<T> observer:onObservers)
                    observer.onError(throwable);
            }

            @Override
            public void onFinished() {
                for (OnObserver<T> observer:onObservers)
                    observer.onFinished();
            }
        });
        return this;
    }
}
