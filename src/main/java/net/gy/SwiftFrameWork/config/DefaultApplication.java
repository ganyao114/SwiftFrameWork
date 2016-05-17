package net.gy.SwiftFrameWork.config;

import android.app.Application;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;

/**
 * Created by gy on 2016/1/30.
 */
public class DefaultApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        IOC.getInstance().init(this);
        IOC.getInstance().invokeMyApps();
    }


}
