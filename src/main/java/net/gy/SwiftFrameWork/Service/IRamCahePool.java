package net.gy.SwiftFrameWork.Service;

/**
 * Created by pc on 16/8/9.
 */
public interface IRamCahePool {
    public void recycle();
    public long getSize();
    public void compress();
    public IRamCahePool getParent();
}
