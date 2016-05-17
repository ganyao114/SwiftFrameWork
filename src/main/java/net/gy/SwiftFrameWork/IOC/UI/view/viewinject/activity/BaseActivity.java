package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.activity;

import android.app.Activity;
import android.os.Bundle;

import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.IOC.Model.net.http.impl.HttpInjectUtil;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
