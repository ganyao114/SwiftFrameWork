package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;

/**
 * Created by gy on 2015/11/30.
 */
public abstract class BaseFragment extends Fragment{

    /**
     * 懒加载控制
     */
    private boolean isLazyLoaded;
    private boolean isPrepared;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    /**
     * 调用懒加载
     */

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    protected void onLazyLoad(){

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
}
