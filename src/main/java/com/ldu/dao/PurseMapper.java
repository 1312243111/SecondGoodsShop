package com.ldu.dao;

import com.ldu.pojo.Purse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurseMapper {
    //生成钱包
    void addPurse(Integer userId);

    Purse selectPurseByUserId(Integer user_id);

   public void updatePurseOfdel(Integer user_id, Float balance);

    public void updatePurseByuserId(Integer userId, Float balance);

    public void updatePurse(Purse purse);

   public List<Purse> selectPurseList();

    public void updatePurseById(Purse purse);

    public Purse selectPurseById(int id);

    public void updateByPrimaryKey(Purse purse);

    public List<Purse> getPagePurseByPurse(@Param("userId")Integer userId, @Param("state")Integer state);
}
