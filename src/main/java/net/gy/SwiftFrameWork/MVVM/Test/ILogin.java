package net.gy.SwiftFrameWork.MVVM.Test;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Annotations.Param;

/**
 * Created by pc on 16/8/29.
 */
public interface ILogin {
    @HttpSrcMethod()
    public void login(@Param("name") String name,@Param("pass") String pass);
}
