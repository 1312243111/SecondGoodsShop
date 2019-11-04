package com.ldu.dao;

import java.util.List;

import com.ldu.pojo.Image;

public interface ImageMapper {



    int insert(Image record);



    List<Image> selectByGoodsPrimaryKey(Integer goodsId);
}