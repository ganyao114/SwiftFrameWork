package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.OnFilter;
import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.impl.FilterProxy;
import net.gy.SwiftFrameWork.Reactive.impl.ObserverProxy;

/**
 * Created by pc on 16/5/16.
 */
public class FilterOperator<T> implements Operator<T,T> {

    private Operator<T,T> preOperator;
    private OnFilter<T> filter;

    public FilterOperator(OnFilter<T> filter,Operator<T,T> preOperator) {
        this.filter = filter;
        this.preOperator = preOperator;
    }

    @Override
    public OnObserver<T> call(final OnObserver<T> iOnObserver) {
        return preOperator.call(new OnObserver<T>() {
            @Override
            public void onSuccess(T t) {
                if (filter.call(t))
                    iOnObserver.onSuccess(t);
            }

            @Override
            public void onError(Throwable throwable) {
                iOnObserver.onError(throwable);
            }

            @Override
            public void onFinished() {
                iOnObserver.onFinished();
            }
        });
    }
}
