package com.ldu.service;

import com.ldu.pojo.Catelog;

public interface CatelogService {
    Catelog selectByPrimaryKey(Integer id);

    int updateCatelogNum(Integer id,Integer number);
}
