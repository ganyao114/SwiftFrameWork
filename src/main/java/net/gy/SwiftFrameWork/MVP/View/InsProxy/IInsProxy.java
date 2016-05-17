package net.gy.SwiftFrameWork.MVP.View.InsProxy;

import android.content.Context;

/**
 * Created by gy on 2016/3/23.
 */
public interface IInsProxy {
    public void injctPresent(Context context);
    public void unRegist(Context context);
    public void setContext(Context context);
    public void setListener(Context context,Class inter,Object listener);
    public void callOnListener(Context context,Class inter);
}
