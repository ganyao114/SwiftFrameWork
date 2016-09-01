package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.MVP.Presenter.Presenter;
import net.gy.SwiftFrameWork.MVP.View.context.IContext;
import net.gy.SwiftFrameWork.MVP.View.context.activity.IActivity;
import net.gy.SwiftFrameWork.MVP.View.context.activity.IActivityCallback;

/**
 * Created by gy on 2015/11/30.
 */
public abstract class BaseFragmentV4<T extends Presenter> extends Fragment{
    protected View view;
    private SparseArray<View> mViews = new SparseArray<View>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = ViewInjectAll.getInstance().inject(this,inflater,container);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewInjectAll.getInstance().remove(this);
    }

    public  <T extends View> T getView(int ViewId) {
        T viewrt = (T) mViews.get(ViewId);
        if (viewrt == null) {
            viewrt = (T)view.findViewById(ViewId);
            mViews.put(ViewId, viewrt);
        }
        return viewrt;
    }

    public T getPresenter(){
        if (getContext() instanceof IContext){
            IContext<T> iContext = (IContext<T>) getContext();
            return iContext.getPresent();
        }
        return null;
    }

}
