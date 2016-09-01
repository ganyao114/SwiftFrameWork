package net.gy.SwiftFrameWork.MVP.View.context.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.gy.SwiftFrameWork.MVP.Presenter.Presenter;
import net.gy.SwiftFrameWork.MVP.View.context.IContext;

/**
 * Created by gy on 2016/1/30.
 */
public class BasicService extends Service implements IContext,IService{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public Presenter getPresent() {
        return null;
    }
}
