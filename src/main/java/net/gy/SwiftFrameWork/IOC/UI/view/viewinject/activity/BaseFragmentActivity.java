package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.IOC.Model.net.http.impl.HttpInjectUtil;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;

/**
 * Created by gy on 2015/11/30.
 */
public abstract class BaseFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectAll.getInstance().inject(this);
        InjectAsycTask.getInstance().inject(this);
        HttpInjectUtil.getInstance().inject(this);
    }

    @Override
    protected void onDestroy() {
        InjectAsycTask.getInstance().remove(this);
        HttpInjectUtil.getInstance().remove(this);
        ViewInjectAll.getInstance().remove(this);
        super.onDestroy();
    }

}
