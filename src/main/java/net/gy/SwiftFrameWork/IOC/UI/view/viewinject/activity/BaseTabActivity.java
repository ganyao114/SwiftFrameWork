package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.activity;

import android.app.TabActivity;
import android.os.Bundle;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.IOC.Model.net.http.impl.HttpInjectUtil;
import net.gy.SwiftFrameWork.IOC.Service.event.impl.EventPoster;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;

/**
 * Created by gy on 2015/11/30.
 */
public abstract class BaseTabActivity extends TabActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectAll.getInstance().inject(this);
        InjectAsycTask.getInstance().inject(this);
        HttpInjectUtil.getInstance().inject(this);
        EventPoster.getInstance().regist(this);
    }

    @Override
    protected void onDestroy() {
        InjectAsycTask.getInstance().remove(this);
        HttpInjectUtil.getInstance().remove(this);
        ViewInjectAll.getInstance().remove(this);
        EventPoster.getInstance().unregist(this);
        super.onDestroy();
    }
}
