package net.gy.SwiftFrameWork.IOC.Service.receiver.entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by gy on 2015/12/2.
 */
public final class BaseBroadCastReceiver extends BroadcastReceiver{

    private IReciver reciver;

    public BaseBroadCastReceiver(IReciver reciver) {
        this.reciver = reciver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        reciver.onReceiver(context,intent);
    }
}
