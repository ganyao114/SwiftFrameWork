package net.gy.SwiftFrameWork.Service.cache.entity;

import android.graphics.Bitmap;

import net.gy.SwiftFrameWork.Model.entity.Entry;
import net.gy.SwiftFrameWork.Service.cache.ICompressHandler;
import net.gy.SwiftFrameWork.Service.cache.exception.CompressException;

/**
 * Created by pc on 16/8/16.
 */
public class BitmapCompressHander implements ICompressHandler<Bitmap,Entry<Integer,Integer>>{

    @Override
    public long compress(Bitmap bitmap, Entry<Integer, Integer> Flag) throws CompressException {
//        if (bitmap == null)
//
        return 0;
    }
}
