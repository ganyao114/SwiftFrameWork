package net.gy.SwiftFrameWork.Service.player.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.gy.SwiftFrameWork.Service.player.audio.entity.AuPalyMode;
import net.gy.SwiftFrameWork.Service.player.audio.entity.AudioInfoTmp;

import java.io.File;

/**
 * Created by gy on 2016/1/15.
 */
public class AudioPlayService extends Service implements IAuPlayerService {

    public IBinder mybinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mybinder = new MyBinder();
    }

    @Override
    public void play(File src, AuPalyMode mode) throws Exception {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void switchTo(long ms) {

    }

    @Override
    public void switchTo(float persent) {

    }

    @Override
    public AudioInfoTmp getPlayInfo() {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mybinder;
    }

    public class MyBinder extends Binder{
        public IAuPlayerService getService(){
            return AudioPlayService.this;
        }
    }

}
