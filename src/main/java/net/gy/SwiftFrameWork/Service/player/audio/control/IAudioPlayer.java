package net.gy.SwiftFrameWork.Service.player.audio.control;

import net.gy.SwiftFrameWork.Service.player.audio.entity.AuPalyMode;
import net.gy.SwiftFrameWork.Service.player.audio.entity.AudioInfoTmp;

import java.io.File;

/**
 * Created by gy on 2016/1/15.
 */
public interface IAudioPlayer {
    public void play(File src, AuPalyMode mode)throws Exception;
    public void pause();
    public void resume();
    public void stop();
    public void switchTo(long ms);
    public void switchTo(float persent);
    public AudioInfoTmp getPlayInfo();
    public void stopService();
}
