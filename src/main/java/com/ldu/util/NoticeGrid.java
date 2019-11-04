package com.ldu.util;

import com.ldu.pojo.Notice;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class NoticeGrid {
    private int current;//当前页面号
    private int roeCount;//每页行数
    private  int tatal;//总页数
    private List<Notice> notice;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRoeCount() {
        return roeCount;
    }

    public void setRoeCount(int roeCount) {
        this.roeCount = roeCount;
    }

    public int getTatal() {
        return tatal;
    }

    public void setTatal(int tatal) {
        this.tatal = tatal;
    }

    public List<Notice> getNotice() {
        return notice;
    }

    public void setNotice(List<Notice> notice) {
        this.notice = notice;
    }
}
