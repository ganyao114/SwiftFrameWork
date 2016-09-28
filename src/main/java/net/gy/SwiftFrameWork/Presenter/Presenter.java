package net.gy.SwiftFrameWork.Presenter;

import android.content.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/26.
 */
public abstract class Presenter implements IPresenter{

    private static Map<Class<? extends Presenter>,Presenter> presenterMap = new ConcurrentHashMap<>();

    private Map<Class<? extends Presenter>,Presenter> childs = new ConcurrentHashMap<>();

    private static Presenter rootPresenter;

    private Presenter parent;

    public static void establish(){
        rootPresenter = new RootPresenter();
        rootPresenter.onPresenterInit(rootPresenter);
        presenterMap.put(rootPresenter.getClass(),rootPresenter);
    }

    public static ExtCall With(Presenter presenter){
        if (presenter == null)
            return new ExtCall(rootPresenter);
        return new ExtCall(presenter);
    }

    public static <T extends Presenter> T find(Class<T> presenterType){
        T presenter = null;
        if (presenterMap.containsKey(presenterType))
            presenter = (T) presenterMap.get(presenterType);
        return presenter;
    }

    protected void init(Presenter presenter){
        onPresenterInit(presenter);
        presenterMap.put(getClass(),this);
        parent = presenter;
    }

    public void destory(Presenter presenter){
        presenterMap.remove(getClass()).onPresenterDestory(presenter);
        if (parent != null)
            parent.notifyChildDestory(this);
        for (Map.Entry<Class<? extends Presenter>,Presenter> entry:childs.entrySet()){
            entry.getValue().destory(this);
        }
    }

    public void notifyChildDestory(Presenter presenter){
        childs.remove(presenter.getClass());
    }


    static class ExtCall{

        private Presenter presenter;

        public ExtCall(Presenter presenter) {
            this.presenter = presenter;
        }

        public <T extends Presenter> T start(Class<T> presenterType){
            T presenter = null;
            try {
                presenter = presenterType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (presenter == null)
                return null;
            Presenter op = presenterMap.get(presenterType);
            if (op != null){
                op.destory(this.presenter);
            }
            presenter.init(this.presenter);
            return presenter;
        }

    }

}
