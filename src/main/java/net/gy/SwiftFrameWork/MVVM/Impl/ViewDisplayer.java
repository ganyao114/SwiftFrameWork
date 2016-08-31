package net.gy.SwiftFrameWork.MVVM.Impl;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import net.gy.SwiftFrameWork.Core.S;
import net.gy.SwiftFrameWork.MVVM.Cache.MethodCache;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBindControl;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBindEntity;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBinderLevel;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewType;
import net.gy.SwiftFrameWork.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/31.
 */
public class ViewDisplayer {


    public static void show(ViewBinderLevel level,Object object,View contener) throws Exception{
        if (level.getElements()!=null) {
            for (Map.Entry<Field, ViewBindEntity> entry : level.getElements().entrySet()) {
                ViewBindEntity bindEntity = entry.getValue();
                Field field = entry.getKey();
                Object fieldValue = field.get(object);
                if (bindEntity.getViewType() == ViewType.Default)
                    bindSingle(bindEntity,fieldValue,contener);
                else
                    bindCollectionView(bindEntity,fieldValue,contener);
            }
        }
        if (level.getObjs()!=null){
            for (Map.Entry<Field, ViewBinderLevel> entry : level.getObjs().entrySet()) {
                Field field = entry.getKey();
                ViewBinderLevel sublevel = entry.getValue();
                Object fieldValue = field.get(object);
                show(sublevel,fieldValue,contener);
            }
        }
    }

    private static void bindCollectionView(ViewBindEntity bindEntity,Object object,View contener){
        int[] ids = bindEntity.getViewids();
        for (int id:ids){
            View view = contener.findViewById(id);
            if (view==null)
                continue;
            if (view instanceof RecyclerView){
                S.ViewUtils.ListBind((RecyclerView) view).bind((List<?>) object);
            }else if (view instanceof ListView||view instanceof RecyclerView){
                S.ViewUtils.ListBind((AbsListView) view).bind((List<?>) object);
            }
        }
    }

    private static void bindSingle(ViewBindEntity bindEntity,Object object,View contener)throws Exception{
        int[] ids = bindEntity.getViewids();
        Method method = bindEntity.getBindMethod();
        for (int id:ids){
            View view = contener.findViewById(id);
            if (view==null)
                continue;
            method.invoke(view,object);
        }
    }

}
