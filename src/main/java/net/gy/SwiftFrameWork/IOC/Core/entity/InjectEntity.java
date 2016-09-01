package net.gy.SwiftFrameWork.IOC.Core.entity;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by gy on 2015/12/7.
 */
public class InjectEntity {

    private View view;
    private WeakReference<View> ViewRefrence;
    private WeakReference<Activity> ActivityRefrence;
    private WeakReference<Fragment> FragmentRefrence;
    private WeakReference<android.support.v4.app.Fragment> FragmentV4Refrence;
    private HashMap<String,InjectCollectionViewBean> collections;

    public WeakReference<View> getViewRefrence() {
        return ViewRefrence;
    }

    public void setViewRefrence(WeakReference<View> viewRefrence) {
        ViewRefrence = viewRefrence;
    }

    public WeakReference<Activity> getActivityRefrence() {
        return ActivityRefrence;
    }

    public void setActivityRefrence(WeakReference<Activity> activityRefrence) {
        ActivityRefrence = activityRefrence;
    }

    public WeakReference<Fragment> getFragmentRefrence() {
        return FragmentRefrence;
    }

    public void setFragmentRefrence(WeakReference<Fragment> fragmentRefrence) {
        FragmentRefrence = fragmentRefrence;
    }

    public WeakReference<android.support.v4.app.Fragment> getFragmentV4Refrence() {
        return FragmentV4Refrence;
    }

    public void setFragmentV4Refrence(WeakReference<android.support.v4.app.Fragment> fragmentV4Refrence) {
        FragmentV4Refrence = fragmentV4Refrence;
    }

    public HashMap<String, InjectCollectionViewBean> getCollections() {
        return collections;
    }

    public void setCollections(HashMap<String, InjectCollectionViewBean> collections) {
        this.collections = collections;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
