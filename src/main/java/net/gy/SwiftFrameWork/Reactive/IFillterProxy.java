package net.gy.SwiftFrameWork.Reactive;

/**
 * Created by pc on 16/5/16.
 */
public interface IFillterProxy<T> {
    public void addFilter(OnFilter<T> filter);
    public void rmFilter(OnFilter<T> filter);
    public void clear();
    public boolean isFilter();
}
