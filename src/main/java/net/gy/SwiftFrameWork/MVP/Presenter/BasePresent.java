package net.gy.SwiftFrameWork.MVP.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.gy.SwiftFrameWork.IOC.Model.net.http.impl.HttpInjectUtil;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;
import net.gy.SwiftFrameWork.MVP.View.context.activity.IActivityCallback;
import net.gy.SwiftFrameWork.MVP.View.context.activity.MBaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by gy on 2015/12/28.
 */
public abstract class BasePresent implements IActivityCallback{

    private WeakReference<MBaseActivity> activityRef;

    public BasePresent() {
        InjectAsycTask.getInstance().inject(this);
        HttpInjectUtil.getInstance().inject(this);
    }

    @Override
    public void OnPresentSeted(MBaseActivity mBaseActivity) {
        this.activityRef = new WeakReference<MBaseActivity>(mBaseActivity);
    }

    @Override
    public void OnCreate(Bundle savedInstanceState,MBaseActivity activity) {

    }

    @Override
    public void OnStart(MBaseActivity activity) {

    }

    @Override
    public void OnPause(MBaseActivity activity) {

    }

    @Override
    public void OnResume(MBaseActivity activity) {

    }

    @Override
    public void OnRestart(MBaseActivity activity) {

    }

    @Override
    public void OnStop(MBaseActivity activity) {

    }

    @Override
    public void OnDestory(MBaseActivity activity) {

    }

    @Override
    public void DestoryPresent() {
        HttpInjectUtil.getInstance().remove(this);
        InjectAsycTask.getInstance().remove(this);
    }

    @Override
    public void OnActivityChangeBefore() {

    }

    @Override
    public void OnActivityChanged() {

    }

    @Override
    public MBaseActivity getActivity(){
        return activityRef.get();
    }

    protected  <T extends View> T getView(int ViewId){
        return getActivity().getView(ViewId);
    }

    protected void startActivity(Intent intent){
        OnActivityChangeBefore();
        getActivity().startActivity(intent);
    }

}
