package net.gy.SwiftFrameWork.MVP.View.context.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;
import net.gy.SwiftFrameWork.MVP.Presenter.BasePresent;

/**
 * Created by gy on 2015/12/27.
 */
public abstract class MBaseActivity extends Activity{
    private SparseArray<View> mViews;
    private IActivityCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresent();
        ViewInjectAll.getInstance().inject(this);
        callback.OnCreate(savedInstanceState,this);
    }

    private void setPresent(){
        callback = getPresent();
        setActivity();
    }

    private void setActivity(){
        callback.OnPresentSeted(this);
        callback.OnActivityChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callback.getActivity() != this)
            setActivity();
        callback.OnStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callback.OnRestart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callback.OnResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        callback.OnPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        callback.OnStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InjectAsycTask.getInstance().remove(this);
        callback.OnDestory(this);
        if (callback.getActivity() == this)
            callback.DestoryPresent();
    }

    public  <T extends View> T getView(int ViewId) {
        T view = (T) mViews.get(ViewId);
        if (view == null) {
            view = (T)findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return view;
    }

    protected abstract BasePresent getPresent();


    public void setText(int id,String str){
        TextView view = getView(id);
        view.setText(str);
    }

}
