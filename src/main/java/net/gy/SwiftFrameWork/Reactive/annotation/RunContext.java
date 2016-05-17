package net.gy.SwiftFrameWork.Reactive.annotation;

import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;

/**
 * Created by pc on 16/5/13.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RunContext {
    RunContextType value()default RunContextType.CurrentThread;
}
