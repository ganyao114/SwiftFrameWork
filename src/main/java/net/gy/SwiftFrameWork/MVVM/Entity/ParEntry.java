package net.gy.SwiftFrameWork.MVVM.Entity;

import net.gy.SwiftFrameWork.Model.entity.Entry;

/**
 * Created by pc on 16/8/29.
 */
public class ParEntry implements Entry<ParType,String>{

    private ParType parType;
    private String parName;

    @Override
    public void set(ParType parType, String s) {
        this.parType = parType;
        this.parName = s;
    }

    @Override
    public ParType getKey() {
        return parType;
    }

    @Override
    public String getValue() {
        return parName;
    }
}
