package net.gy.SwiftFrameWork.IOC.Model.local.database.annotation;

import net.gy.SwiftFrameWork.IOC.Model.local.database.entity.DBValueMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/1/23.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DBInjectBase(DBValueMode.MainKey)
public @interface DBMainKey {
}
