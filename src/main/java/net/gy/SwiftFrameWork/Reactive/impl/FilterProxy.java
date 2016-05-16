package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.IFillterProxy;
import net.gy.SwiftFrameWork.Reactive.OnFilter;

import java.util.Vector;

/**
 * Created by pc on 16/5/16.
 */
public class FilterProxy<T> implements OnFilter<T>,IFillterProxy<T>{

    private Vector<OnFilter<T>> filters;

    public FilterProxy() {
        filters = new Vector<OnFilter<T>>();
    }

    @Override
    public Boolean call(T t) {
        for (OnFilter<T> filter:filters){
            if (!filter.call(t)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void addFilter(OnFilter<T> filter) {
        filters.add(filter);
    }

    @Override
    public void rmFilter(OnFilter<T> filter) {
        filters.remove(filter);
    }

    @Override
    public void clear() {
        filters.clear();
    }

    @Override
    public boolean isFilter() {
        if (filters.size() == 0)
            return false;
        else
            return true;
    }
}
