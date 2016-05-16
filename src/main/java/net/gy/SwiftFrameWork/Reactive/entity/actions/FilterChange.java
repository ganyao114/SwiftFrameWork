package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.OnFilter;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

/**
 * Created by pc on 16/5/17.
 */
public class FilterChange<T> extends Change<T,T>{
    private OnFilter<T> filter;
    public FilterChange(OnObserver<T> preChange,OnFilter<T> filter) {
        super(preChange);
        this.filter = filter;
    }

    @Override
    public void onSuccess(T t) {
        if (filter.call(t))
            preChange.onSuccess(t);
    }
}
