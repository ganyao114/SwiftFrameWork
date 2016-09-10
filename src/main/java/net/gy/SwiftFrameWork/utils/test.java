package net.gy.SwiftFrameWork.utils;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gy939 on 2016/9/8.
 */
public class test {
    private Map<Integer,String> map = Collections.synchronizedMap(new HashMap<Integer, String>());

    private long starttime;

    private AtomicInteger count = new AtomicInteger(200);

    Runnable readrun = new Runnable() {
        @Override
        public void run() {
            int i = 10000;
            while (i > 0){
                map.get(i);
                i--;
            }
            Log.e("gy","read-mapsizLog.ee="+map.size());
            if(count.decrementAndGet() == 0)
                Log.e("gy","time="+ (System.currentTimeMillis() - starttime +"ms"));
        }
    };

    Runnable writerun = new Runnable() {
        @Override
        public void run() {
            int i = 10000;
            while (i > 0){
                map.put(i,i+"");
                i--;
            }
            Log.e("gy","write-mapsize="+map.size());
            if(count.decrementAndGet() == 0)
                Log.e("gy","time="+ (System.currentTimeMillis() - starttime + "ms"));
        }
    };

    public void run(){
        starttime = System.currentTimeMillis();
        for(int i = 0;i < 100;i ++){
            new Thread(writerun).start();
            new Thread(readrun).start();
        }
    }

}
