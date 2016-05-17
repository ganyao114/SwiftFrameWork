package net.gy.SwiftFrameWork.Service.thread.templet.configs;

public class HttpTheadConfigBean {

    public boolean isLoop;
    public int tickTime;
    public String ErrorMsgLevel1;
    public String ErrorMsgLevel2;
    public String ErrorMsgLevel3;

    public HttpTheadConfigBean(boolean isLoop, int tickTime,
                               String ErrorMsgLevel1, String ErrorMsgLevel2,
                               String ErrorMsgLevel3) {
        // TODO Auto-generated constructor stub
        this.isLoop = isLoop;
        this.tickTime = tickTime;
        this.ErrorMsgLevel1 = ErrorMsgLevel1;
        this.ErrorMsgLevel2 = ErrorMsgLevel2;
        this.ErrorMsgLevel3 = ErrorMsgLevel3;
    }

}
