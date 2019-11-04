package com.ldu.service;

import com.ldu.pojo.Purse;

import java.util.List;

public interface PurseService {
    //生成钱包
 public void addPurse(Integer id);
    /**
     * 根据用户id获取钱包信息
     * @param user_id
     * @return
     */
  public  Purse getPurseByUserId(Integer user_id);

   public void updatePurseOfdel(Integer user_id, Float balance);

   public void updatePurseByuserId(Integer userId, Float balance);
    /**
     * 充值或提现
     * @param purse
     */
   public void updatePurse(Purse purse);

   public int getPurseNum();

   public List<Purse> getPagePurse(int pageNum, int pageSize);

   public List<Purse> getPagePurseByPurse(Integer userId, Integer state, int pageNum, int pageSize);

    public void updatePursePassById(Integer id, Purse purse);

    public void updatePurseRefuseById(Integer id,Purse purse);

    public Purse getPurseById(int id);

 public void updateByPrimaryKey(Integer id, Purse purse);
}
