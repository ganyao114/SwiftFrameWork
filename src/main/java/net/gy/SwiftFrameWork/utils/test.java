package net.gy.SwiftFrameWork.utils;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/8.
 */
public class test {
    private Map<Integer,String> map = Collections.synchronizedMap(new HashMap<Integer, String>());

    Runnable readrun = new Runnable() {
        @Override
        public void run() {
            int i = 1000000;
            while (i > 0){
                map.get(i);
                i--;
            }
            Log.e("gy","mapsize"+map.size());
        }
    };

    Runnable writerun = new Runnable() {
        @Override
        public void run() {
            int i = 1000000;
            while (i > 0){
                map.put(i,i+"");
                i--;
            }
            Log.e("gy","mapsize"+map.size());
        }
    };

    public void run(){
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
        new Thread(readrun).start();
        new Thread(writerun).start();
    }

}
