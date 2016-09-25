package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Interface.IHttpDriver;

/**
 * Created by gy939 on 2016/9/24.
 */
public class HttpDriverFactory {
    public static IHttpDriver getDriver(Class<? extends IHttpDriver> clazz){
        IHttpDriver driver = null;
        try {
            driver = clazz.newInstance();
        }catch (Exception e){
            return null;
        }
        return driver;
    }
}
