package net.gy.SwiftFrameWork.Core;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.IOC.Model.local.resource.entity.ResType;
import net.gy.SwiftFrameWork.IOC.Model.local.resource.impl.ResLoader;
import net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.impl.SharePrefrenceUtils;
import net.gy.SwiftFrameWork.IOC.Model.net.http.impl.HttpInjectUtil;
import net.gy.SwiftFrameWork.IOC.Service.event.impl.EventPoster;
import net.gy.SwiftFrameWork.IOC.Service.thread.impl.InjectAsycTask;
import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.impl.ListBinder;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectAll;
import net.gy.SwiftFrameWork.Service.loader.imgloader.IImageLoader;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.ImgLoadConfigs;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;

/**
 * Created by gy on 2016/5/6.
 */
public class S {

    public static void init(Application application){
        IOC.getInstance().init(application);
    }

    public static EventPoster Event(){
        return EventPoster.getInstance();
    }

    public static class AsyckTask{

        public static InjectAsycTask getInstance(){
            return InjectAsycTask.getInstance();
        }
        public static InjectAsycTask.ExtCall With(Object object, String key){
            return InjectAsycTask.With(object, key);
        }

        public static void Inject(Object object){
            InjectAsycTask.Inject(object);
        }
        public static void Remove(Object object){
            InjectAsycTask.Remove(object);
        }
    }

    public static class HttpUtils{

        public static HttpInjectUtil getInstance(){
            return HttpInjectUtil.getInstance();
        }

        public static void Inject(Object object){
            HttpInjectUtil.Inject(object);
        }

        public static void Remove(Object object){
            HttpInjectUtil.Remove(object);
        }

        public static HttpInjectUtil.ExtCall With(Object object,String key){
            return HttpInjectUtil.With(object, key);
        }
    }

    public static class SharePrefrence{
        public static boolean Save(Object object){
            return SharePrefrenceUtils.Save(object);
        }
        public static boolean Get(Object object){
            return SharePrefrenceUtils.Get(object);
        }
        public static boolean Get(Context context,Object object){
            return SharePrefrenceUtils.Get(context,object);
        }
        public static boolean Remove(Object object,Object... fields){
            return SharePrefrenceUtils.getInstance().remove(object,fields);
        }
    }

    public static class ViewUtils{
        public static void Inject(Object object){
            ViewInjectAll.Inject(object);
        }
        public static void Remove(Object object){
            ViewInjectAll.Remove(object);
        }
        public static ViewInjectAll.ExtCall With(Object object,String key){
            return ViewInjectAll.With(object,key);
        }

        public static ViewInjectAll getInstance(){
            return ViewInjectAll.getInstance();
        }

        public static ListBinder.ExtCall ListBind(AbsListView view){
            return ListBinder.With(view);
        }

        public static ListBinder.ExtCall ListBind(RecyclerView view){
            return ListBinder.With(view);
        }
    }

    public static Object loadRes(ResType type, Context context, int id){
        return ResLoader.loadRes(type,context,id);
    }

    public static class Reactive{

    }

    public static IImageLoader loadImg(Context context){
        return ImageLoader.getInstance(context,new ImgLoadConfigs());
    }

    public static Context getAppContext(){
        return IOC.getInstance().getApplication();
    }



}
