package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.OnFilter;

import java.util.Vector;

/**
 * Created by pc on 16/5/16.
 */
public class FilterProxy<T> implements OnFilter<T>{

    private Vector<OnFilter<T>> filters;

    public FilterProxy() {
    }

    @Override
    public Boolean call(T t) {
        return null;
    }
}
