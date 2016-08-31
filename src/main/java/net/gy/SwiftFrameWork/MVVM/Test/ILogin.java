package net.gy.SwiftFrameWork.MVVM.Test;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Annotations.Param;

/**
 * Created by pc on 16/8/29.
 */
public interface ILogin {
    @HttpSrcMethod(url = "http://www.baidu.com")
    public TestPojo login(@Param("name") String name,@Param("pass") String pass,@Param("par3") String par3);
    @HttpSrcMethod(url = "http://www.baidu.com")
    public TestPojo regist(@Param("name") String name,@Param("pass") String pass,@Param("par3") String par3);
}
