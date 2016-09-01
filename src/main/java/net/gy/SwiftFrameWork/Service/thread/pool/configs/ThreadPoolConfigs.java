package net.gy.SwiftFrameWork.Service.thread.pool.configs;

/**
 * Created by gy on 2015/11/6.
 */
public class ThreadPoolConfigs {

    private final int DFT_POOL_SIZE = 4;
    private final int DFT_MAX_POOL_SIZE = 6;
    private final int DFT_KEEP_ALIVE_TIME = 4;
    private int POOL_SIZE;
    private int MAX_POOL_SIZE;
    private int KEEP_ALIVE_TIME;

    public ThreadPoolConfigs() {
        POOL_SIZE = DFT_POOL_SIZE;
        MAX_POOL_SIZE = DFT_MAX_POOL_SIZE;
        KEEP_ALIVE_TIME = DFT_KEEP_ALIVE_TIME;
    }

    public ThreadPoolConfigs(int POOL_SIZE, int MAX_POOL_SIZE, int KEEP_ALIVE_TIME) {
        this.POOL_SIZE = POOL_SIZE;
        this.MAX_POOL_SIZE = MAX_POOL_SIZE;
        this.KEEP_ALIVE_TIME = KEEP_ALIVE_TIME;
    }

    public int getPOOL_SIZE() {
        return POOL_SIZE;
    }

    public void setPOOL_SIZE(int POOL_SIZE) {
        this.POOL_SIZE = POOL_SIZE;
    }

    public int getMAX_POOL_SIZE() {
        return MAX_POOL_SIZE;
    }

    public void setMAX_POOL_SIZE(int MAX_POOL_SIZE) {
        this.MAX_POOL_SIZE = MAX_POOL_SIZE;
    }

    public int getKEEP_ALIVE_TIME() {
        return KEEP_ALIVE_TIME;
    }

    public void setKEEP_ALIVE_TIME(int KEEP_ALIVE_TIME) {
        this.KEEP_ALIVE_TIME = KEEP_ALIVE_TIME;
    }
}
