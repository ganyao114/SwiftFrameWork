package net.gy.SwiftFrameWork.IOC.Service.receiver.entity;

/**
 * Created by gy on 2015/12/1.
 */
public class ReceiverBean {
    private BaseBroadCastReceiver receiver;

    public BaseBroadCastReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(BaseBroadCastReceiver receiver) {
        this.receiver = receiver;
    }
}
