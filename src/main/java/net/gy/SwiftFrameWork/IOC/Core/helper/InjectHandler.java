package net.gy.SwiftFrameWork.IOC.Core.helper;

import android.app.Activity;

/**
 * Created by gy939 on 2016/9/25.
 */
public interface InjectHandler {
    public void inject(Activity activity);
    public void remove(Activity activity);
}
