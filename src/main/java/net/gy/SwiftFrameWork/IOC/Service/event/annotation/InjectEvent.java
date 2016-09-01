package net.gy.SwiftFrameWork.IOC.Service.event.annotation;

import net.gy.SwiftFrameWork.IOC.Service.event.entity.EventPrio;
import net.gy.SwiftFrameWork.IOC.Service.event.entity.EventThreadType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2015/11/24.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectEvent {
    EventThreadType type();
    EventPrio prio()default EventPrio.Default;
    boolean filter()default false;
}
