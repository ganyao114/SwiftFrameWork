package net.gy.SwiftFrameWork.IOC.Core.impl;

import android.app.Application;

import net.gy.SwiftFrameWork.IOC.Core.entity.InstrumentationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2015/11/18.
 */
public class IOC {

    private Application application;
    private List<Application> myapps;
    private static IOC ioc;
    private InstrumentationBean instrumentation;
    private Thread intThread;

    public static IOC getInstance() {
        synchronized (IOC.class) {
            if (ioc == null) {
                ioc = new IOC();
            }
        }
        return ioc;
    }

    private IOC() {
        myapps = new ArrayList<Application>();
    }

    public Application getApplication() {
        return application;
    }

    public boolean addMyApplication(Application application){
        myapps.add(application);
        return true;
    }

    public boolean invokeMyApps(){
        for (Application app:myapps)
            app.onCreate();
        return true;
    }

    public void init(Application application) {
        this.application = application;
        /*
        //读取配置
        intThread = new Thread(new IntThread());
        intThread.start();
        ResourceFactory.setRclass(application);
        Object mainThread = KernelObject.declaredGet(application.getBaseContext(), "mMainThread");
        Field instrumentationField = KernelReflect.declaredField(mainThread.getClass(), "mInstrumentation");
        instrumentation = new InstrumentationBean();
        KernelObject.copy(KernelReflect.get(mainThread, instrumentationField), instrumentation);
        KernelReflect.set(mainThread, instrumentationField, instrumentation);

        */
    }

    class ExtCall{

    }
}
