package net.gy.SwiftFrameWork.IOC.Core.annotation;

import net.gy.SwiftFrameWork.IOC.Core.entity.CollectionViewType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2015/12/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectCollectionView {
    int listlayout();
    int itemlayout();
    CollectionViewType type() default CollectionViewType.ListView;
}
