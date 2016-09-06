package net.gy.SwiftFrameWork.Service.cache.impl;

import net.gy.SwiftFrameWork.Service.cache.Utils.SerialNo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by gy on 2016/9/6.
 */
public class Swapper {

    public static String save(Serializable tar,String path){
        if (tar == null)
            return null;
        String serialNo = SerialNo.getSerial(tar);
        try {
            FileOutputStream outputStream = new FileOutputStream(path+"/" + serialNo);
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(tar);
            os.flush();
            os.close();
        } catch (Exception e) {
            return null;
        }
        return serialNo;
    }

    public static Serializable get(String path){
        try {
            FileInputStream fs = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fs);
            Serializable object = (Serializable) ois.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
