package net.gy.SwiftFrameWork.Service.cache.entity;


/**
 * Created by pc on 16/8/13.
 */
public class C {
    public static class route{
        public static Object[] test = {"Test",new Integer(1)};
    }

    public static Object[] link(Object[] a,Object[] b){
        Object[] c= new String[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}
