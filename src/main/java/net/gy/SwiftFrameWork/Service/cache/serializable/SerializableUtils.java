package net.gy.SwiftFrameWork.Service.cache.serializable;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by gy on 2016/4/8.
 */
public class SerializableUtils implements ISerializableUtils {

    private String path;

    public SerializableUtils(String path) {
        this.path = path;
    }

    @Override
    public boolean saveObject(Object object,String name) throws Exception {
        if (!detected(object))
            throw new IllegalArgumentException();
        preparePath();
        File file = new File(path + "/" + name);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        fos.close();
        return true;
    }

    private void preparePath() throws IOException {
        if (path == null)
            throw new IllegalArgumentException();
        File file = new File(path);
        if (!file.mkdirs())
            throw new IOException("无法创建文件夹");
    }

    private boolean detected(Object object) {
        if (object instanceof Serializable||object instanceof Externalizable)
            return true;
        return false;
    }

    @Override
    public Object readObject(String name) throws Exception {
        File file = new File(path + "/" + name);
        if (!file.exists())
            throw new FileNotFoundException();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        ois.close();
        fis.close();
        return object;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }
}
