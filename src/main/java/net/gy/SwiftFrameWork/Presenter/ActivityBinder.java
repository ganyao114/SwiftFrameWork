package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;

import java.util.List;

/**
 * Created by gy939 on 2016/9/28.
 */
public class ActivityBinder {

    private Class<? extends Activity> type;
    private List<LifeInvokerEntity>[] lifes = new List[7];

    public Class<? extends Activity> getType() {
        return type;
    }

    public void setType(Class<? extends Activity> type) {
        this.type = type;
    }

    public List<LifeInvokerEntity>[] getLifes() {
        return lifes;
    }

    public void setLifes(List<LifeInvokerEntity>[] lifes) {
        this.lifes = lifes;
    }
}
