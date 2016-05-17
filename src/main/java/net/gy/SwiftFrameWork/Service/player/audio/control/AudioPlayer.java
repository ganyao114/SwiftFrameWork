package net.gy.SwiftFrameWork.Service.player.audio.control;

import net.gy.SwiftFrameWork.Service.player.audio.entity.AuPalyMode;
import net.gy.SwiftFrameWork.Service.player.audio.entity.AudioInfoTmp;

import java.io.File;

/**
 * Created by gy on 2016/1/18.
 */
public class AudioPlayer implements IAudioPlayer {

    private static IAudioPlayer player;

    public static IAudioPlayer getInstance(){
        synchronized (AudioPlayer.class) {
            if (player == null)
                player = new AudioPlayer();
        }
        return player;
    }

    @Override
    public void play(File src, AuPalyMode mode) throws Exception {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void switchTo(long ms) {

    }

    @Override
    public void switchTo(float persent) {

    }

    @Override
    public AudioInfoTmp getPlayInfo() {
        return null;
    }

    @Override
    public void stopService() {

    }
}
