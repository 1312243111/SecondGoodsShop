package com.ldu.service.impl;

import com.github.pagehelper.PageHelper;
import com.ldu.dao.NoticeMapper;
import com.ldu.pojo.Notice;
import com.ldu.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("NoticeService")
public class NoticeSeerviceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
//显示求购信息
    public List<Notice> getNoticeList() {

        return noticeMapper.getNoticeList();
    }

    //发布求购信息
    public void insertSelective(Notice notice) {
        noticeMapper.insertSelective(notice);
    }
    //获取所有求购信息数量
//    public int getNoticeNum() {
//        List<Notice> notices=noticeMapper.getNoticeList();
//        return notices.size();
//    }
//
//    //获取当前页的求购信息
//    public List<Notice> getPageUser(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum,pageSize);//分页核心代码
//        List<Notice> notices=noticeMapper.getNoticeList();
//        return notices;
//    }
}
