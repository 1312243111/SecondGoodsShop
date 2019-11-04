package com.ldu.service;

import com.ldu.pojo.Focus;

import java.util.List;

public interface FocusService {
    /**
     * 根据用户的id，查询出该用户关注的所有闲置
     *
     * @return
     */
    public List<Focus> getFocusByUserId(Integer user_id);

    /**
     * 添加我的关注
     *
     */
    public void addFocusByUserIdAndId(Integer goods_id, Integer user_id);

    /**
     * 删除关注
     * @param goods_id
     * @param user_id
     */
   public void deleteFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id);
}
