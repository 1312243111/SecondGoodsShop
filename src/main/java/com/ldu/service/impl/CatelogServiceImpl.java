package com.ldu.service.impl;

import com.ldu.dao.CatelogMapper;
import com.ldu.pojo.Catelog;
import com.ldu.service.CatelogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("catelogService")
public class CatelogServiceImpl implements CatelogService {
    @Resource
    private CatelogMapper catelogMapper;
    @Override
    public Catelog selectByPrimaryKey(Integer id){
        Catelog catelog = catelogMapper.selectByPrimaryKey(id);
        return catelog;
    }

    public int updateCatelogNum(Integer id,Integer number) {
        return catelogMapper.updateCatelogNum(id,number);
    }
}
