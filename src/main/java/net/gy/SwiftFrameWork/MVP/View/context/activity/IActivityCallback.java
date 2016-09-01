package net.gy.SwiftFrameWork.MVP.View.context.activity;

import android.os.Bundle;

/**
 * Created by gy on 2015/12/28.
 */
public interface IActivityCallback {
    public void OnCreate(Bundle savedInstanceState,MBaseActivity activity);
    public void OnStart(MBaseActivity activity);
    public void OnPause(MBaseActivity activity);
    public void OnResume(MBaseActivity activity);
    public void OnRestart(MBaseActivity activity);
    public void OnStop(MBaseActivity activity);
    public void OnDestory(MBaseActivity activity);
    public void OnActivityChangeBefore();
    public void OnActivityChanged();
    public void OnPresentSeted(MBaseActivity mBaseActivity);
    public MBaseActivity getActivity();
    public void DestoryPresent();
}
