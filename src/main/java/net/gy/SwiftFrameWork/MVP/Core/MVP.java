package net.gy.SwiftFrameWork.MVP.Core;

import net.gy.SwiftFrameWork.MVP.Entity.MVPEntity;

import java.util.WeakHashMap;

/**
 * Created by gy on 2016/3/8.
 */
public class MVP {
    public static WeakHashMap<Class,MVPEntity> mvpEntity = new WeakHashMap<Class,MVPEntity>();
}
