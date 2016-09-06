package net.gy.SwiftFrameWork.UI.ClickFilter;

import android.view.View;

/**
 * Created by gy on 2016/9/6.
 */
public class ClickProxy implements View.OnClickListener{

    private View.OnClickListener origin;
    private long lastclick = 0;
    private long timems; //ms

    public ClickProxy(View.OnClickListener origin, long timems) {
        this.origin = origin;
        this.timems = timems;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems){
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        }
    }
}
