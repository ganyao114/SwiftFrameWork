package net.gy.SwiftFrameWork.MVVM.Interface;

import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;

/**
 * Created by pc on 16/8/29.
 */
public interface IFilter<I,O> {
    public O filter(I i) throws Exception;
}
