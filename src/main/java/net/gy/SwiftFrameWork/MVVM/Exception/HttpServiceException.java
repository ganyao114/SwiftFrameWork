package net.gy.SwiftFrameWork.MVVM.Exception;

/**
 * Http业务层异常
 * Created by pc on 16/8/29.
 */
public class HttpServiceException extends BaseServiceException{
    public HttpServiceException(String detailMessage) {
        super(detailMessage);
    }
}
