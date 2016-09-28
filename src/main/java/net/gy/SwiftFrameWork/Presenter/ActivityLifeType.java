package net.gy.SwiftFrameWork.Presenter;

/**
 * Created by gy939 on 2016/9/26.
 */
public enum ActivityLifeType {

    OnCreate(0),
    OnStart(1),
    OnResume(2),
    OnPause(3),
    OnStop(4),
    OnSave(5),
    OnDestory(6);

    private int value = -1;

    private ActivityLifeType(int value){
        this.value = value;
    }

    public int value() {
        return this.value;
    }


}
