package net.gy.SwiftFrameWork.Service.cache;

import net.gy.SwiftFrameWork.Service.cache.exception.CompressException;

/**
 * Created by pc on 16/8/16.
 */
public interface ICompressHandler<T,F> {
    public long compress(T t,F Flag) throws CompressException;
}
