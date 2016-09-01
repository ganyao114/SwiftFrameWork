package net.gy.SwiftFrameWork.MVP.Presenter;


import android.app.Activity;
import android.content.Context;

import net.gy.SwiftFrameWork.MVP.View.context.activity.IActivity;

/**
 * Created by gy on 2016/1/30.
 */
public interface IPresenterCallBack {
    public void OnPresentInited(Context context);
    public void OnPresentSeted(Context context);
    public Context getContext();
    public Activity getActivityRaw();
    public IActivity getActivityInter();
    public void DestoryPresent();
}
