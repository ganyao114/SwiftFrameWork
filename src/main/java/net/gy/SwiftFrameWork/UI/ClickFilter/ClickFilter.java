package net.gy.SwiftFrameWork.UI.ClickFilter;

import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by gy on 2016/9/6.
 */
public class ClickFilter {
    public static void setFilter(View view,long ms){
        try {
            Field field = View.class.getDeclaredField("mListenerInfo");
            field.setAccessible(true);
            Class listInfoType = field.getType();
            Object listinfo = field.get(view);
            Field onclickField = listInfoType.getField("mOnClickListener");
            View.OnClickListener origin = (View.OnClickListener) onclickField.get(listinfo);
            onclickField.set(listinfo,new ClickProxy(origin,ms));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
