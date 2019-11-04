package com.ldu.dao;

import com.ldu.pojo.Catelog;
import org.apache.ibatis.annotations.Param;

public interface CatelogMapper {
    Catelog selectByPrimaryKey(Integer id);

    int updateCatelogNum(@Param("id") Integer id, @Param("number") Integer number);
}
