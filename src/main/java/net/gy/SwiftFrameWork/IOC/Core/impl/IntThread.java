package net.gy.SwiftFrameWork.IOC.Core.impl;

import net.gy.SwiftFrameWork.IOC.Core.parase.AnnotationFactory;

/**
 * Created by gy on 2015/11/19.
 */
public class IntThread implements Runnable {
    private AnnotationFactory factory;

    @Override
    public void run() {
        factory = AnnotationFactory.getInstance();
        factory.getContextAnnotations();
    }
}
