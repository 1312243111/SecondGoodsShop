package com.ldu.service.impl;

import com.github.pagehelper.PageHelper;
import com.ldu.dao.PurseMapper;
import com.ldu.pojo.Purse;
import com.ldu.service.PurseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("PurserService")
public class PurseServiceImpl implements PurseService {
    @Resource
    private PurseMapper perseMapper;

    //生成钱包
    public void addPurse(Integer userId) {
        perseMapper.addPurse(userId);
    }

    /**
     * 根据用户id获取钱包信息
     * @param user_id
     * @return
     */
    public Purse getPurseByUserId(Integer user_id) {
        return perseMapper.selectPurseByUserId(user_id);
    }

    @Override
    public void updatePurseOfdel(Integer user_id, Float balance) {
        perseMapper.updatePurseOfdel(user_id,balance);
    }

    @Override
    public void updatePurseByuserId(Integer userId, Float balance) {
        perseMapper.updatePurseByuserId(userId, balance);
    }

    @Override
    public void updatePurse(Purse purse) {
        perseMapper.updatePurse(purse);
    }

    @Override
    public int getPurseNum() {
        List<Purse> purse = perseMapper.selectPurseList();
        return purse.size();
    }

    @Override
    public List<Purse> getPagePurse(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Purse> purse = perseMapper.selectPurseList();
        return purse;
    }

    @Override
    public List<Purse> getPagePurseByPurse(Integer userId, Integer state, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Purse> purse =perseMapper.getPagePurseByPurse(userId,state);
        return purse;
    }

    @Override
    public void updatePursePassById(Integer id,Purse purse) {
        purse.setId(id);
        perseMapper.updatePurseById(purse);

    }


    @Override
    public void updatePurseRefuseById(Integer id,Purse purse) {
        purse.setId(id);
        perseMapper.updatePurseById(purse);

    }

    @Override
    public Purse getPurseById(int id) {
        return perseMapper.selectPurseById(id);
    }


    @Override
    public void updateByPrimaryKey(Integer id, Purse purse) {
        purse.setId(id);
        perseMapper.updateByPrimaryKey(purse);

    }
}
