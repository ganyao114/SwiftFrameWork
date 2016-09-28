package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;

import java.util.List;
import java.util.Vector;

/**
 * Created by gy939 on 2016/9/28.
 */
public class ActivityBinder {

    private Class<? extends Activity> type;
    private Vector<LifeInvokerEntity>[] lifes = new Vector[7];

    public Class<? extends Activity> getType() {
        return type;
    }

    public void setType(Class<? extends Activity> type) {
        this.type = type;
    }

    public Vector<LifeInvokerEntity>[] getLifes() {
        return lifes;
    }

    public void setLifes(Vector<LifeInvokerEntity>[] lifes) {
        this.lifes = lifes;
    }
}
