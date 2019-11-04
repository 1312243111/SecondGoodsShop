package com.ldu.dao;

import com.ldu.pojo.Notice;

import java.util.List;

public interface NoticeMapper {
    //显示求购信息
    public List<Notice> getNoticeList();
//发布求购信息
    void insertSelective(Notice notice);
}
