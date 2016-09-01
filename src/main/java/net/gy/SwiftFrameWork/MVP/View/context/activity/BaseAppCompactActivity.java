package net.gy.SwiftFrameWork.MVP.View.context.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;
import net.gy.SwiftFrameWork.MVP.Presenter.IPresenterCallBack;
import net.gy.SwiftFrameWork.MVP.Presenter.Presenter;
import net.gy.SwiftFrameWork.MVP.View.context.IContext;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnCreatedListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnDestoryListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnPauseListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnRestartListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnResumeListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnStartListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnStopListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2016/2/24.
 */
public abstract class BaseAppCompactActivity<P extends Presenter> extends AppCompatActivity implements IActivity,IContext<P>{
    //通过getView管理的View集合
    private SparseArray<View> mViews;
    //生命周期的监听集合
    private Map<Class,Object> listeners;
    //对应的Presenter的引用
    private WeakReference<IPresenterCallBack> callbackRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<View>();
        listeners = new HashMap<Class,Object>();

        setPresent();
        //注解框架
        ViewInjectAll.getInstance().inject(this);


        ActivityOnCreatedListener listener = (ActivityOnCreatedListener) listeners.get(ActivityOnCreatedListener.class);
        if (listener!=null)
            listener.ActivityOnCreated(savedInstanceState,this);

    }

    private void setPresent(){
        IPresenterCallBack callBack = Presenter.regist(this);
        if (callBack == null)
            return;
        callbackRef = new WeakReference<IPresenterCallBack>(callBack);
        setActivity();
    }

    private void setActivity(){
        callbackRef.get().OnPresentSeted(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callbackRef.get().getActivityRaw() != this)
            setActivity();
        ActivityOnStartListener listener = (ActivityOnStartListener) listeners.get(ActivityOnStartListener.class);
        if (listener != null)
            listener.ActivityOnStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ActivityOnRestartListener listener = (ActivityOnRestartListener) listeners.get(ActivityOnRestartListener.class);
        if (listener != null)
            listener.ActivityOnRestart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityOnResumeListener listener = (ActivityOnResumeListener) listeners.get(ActivityOnResumeListener.class);
        if (listener != null)
            listener.ActivityOnResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityOnPauseListener listener = (ActivityOnPauseListener) listeners.get(ActivityOnPauseListener.class);
        if (listener != null)
            listener.ActivityOnPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityOnStopListener listener = (ActivityOnStopListener) listeners.get(ActivityOnStopListener.class);
        if (listener != null)
            listener.ActivityOnStop(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        InjectAsycTask.getInstance().remove(this);

        ActivityOnDestoryListener listener = (ActivityOnDestoryListener) listeners.get(ActivityOnDestoryListener.class);
        if (listener!=null)
            listener.ActivityOnDestory(this);

        Presenter.unregist(this);
    }

    public  <T extends View> T getView(int ViewId) {
        T view = (T) mViews.get(ViewId);
        if (view == null) {
            view = (T)findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return view;
    }


    public void setText(int id,String str){
        TextView view = getView(id);
        view.setText(str);
    }

    public void setImg(int id, Bitmap bitmap){
        ImageView imageView = getView(id);
        imageView.setImageBitmap(bitmap);
    }

    public void setOnCreateListener(ActivityOnCreatedListener listener){
        listeners.put(ActivityOnCreatedListener.class,listener);
    }

    public void setOnDestroyListener(ActivityOnDestoryListener listener){
        listeners.put(ActivityOnDestoryListener.class,listener);
    }

    public void setOnStartListener(ActivityOnStartListener listener){
        listeners.put(ActivityOnStartListener.class,listener);
    }

    public void setOnRestartListener(ActivityOnRestartListener listener){
        listeners.put(ActivityOnRestartListener.class,listener);
    }

    public void setOnResumeListener(ActivityOnResumeListener listener){
        listeners.put(ActivityOnResumeListener.class,listener);
    }

    public void setOnStopListener(ActivityOnStopListener listener){
        listeners.put(ActivityOnStopListener.class,listener);
    }

    public void setOnPauseListener(ActivityOnPauseListener listener){
        listeners.put(ActivityOnPauseListener.class,listener);
    }

    @Override
    public P getPresent() {
        return (P) callbackRef.get();
    }
}
