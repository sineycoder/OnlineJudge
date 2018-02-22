package cn.jsut.coder.po;

import java.io.Serializable;

/**
 * @author:
 * @date:2018/1/17 8:57
 * @version:
 * @copyright:
 */
//涉及流，需要序列化
public class ProgressEntity implements Serializable{

    private long upByte = 0L;
    private long upSize = 0L;
    private int upItems;

    @Override
    public String toString() {
        return "ProgressEntity{" +
                "upByte=" + upByte +
                ", upSize=" + upSize +
                ", upItems=" + upItems +
                '}';
    }

    public long getUpByte() {
        return upByte;
    }

    public void setUpByte(long upByte) {
        this.upByte = upByte;
    }

    public long getUpSize() {
        return upSize;
    }

    public void setUpSize(long upSize) {
        this.upSize = upSize;
    }

    public int getUpItems() {
        return upItems;
    }

    public void setUpItems(int upItems) {
        this.upItems = upItems;
    }
}
