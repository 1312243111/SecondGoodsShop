package com.ldu.dao;

import com.ldu.pojo.Focus;

import java.util.List;

public interface FocusMapper {
    public List<Focus> getFocusByUserId(Integer user_id);

    /**
     * 添加关注
     * @param goods_id
     * @param user_id
     */
    public void addFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id);

    /**
     * 删除关注
     * @param goods_id
     * @param user_id
     */
  public void deleteFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id);
}
