package com.ldu.service;

import com.ldu.pojo.Notice;

import java.util.List;

public interface NoticeService {
//获取所有求购信息数量
    //int getNoticeNum();
    //获取出当前页求购信息
   // List<Notice> getPageUser(int pageNum, int pageSize);
//查看求购信息
    List<Notice> getNoticeList();
//发布求购信息
    void insertSelective(Notice notice);
}
