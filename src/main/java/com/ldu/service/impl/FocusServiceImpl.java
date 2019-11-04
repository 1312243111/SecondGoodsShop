package com.ldu.service.impl;

import com.ldu.dao.FocusMapper;
import com.ldu.pojo.Focus;
import com.ldu.service.FocusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("focusService")
public class FocusServiceImpl implements FocusService {
    @Resource
    private FocusMapper focusMapper;

    @Override
    public List<Focus> getFocusByUserId(Integer user_id) {
        List<Focus> focusList = focusMapper.getFocusByUserId(user_id);

        return focusList;
    }


    public void addFocusByUserIdAndId(Integer goods_id, Integer user_id) {

        focusMapper.addFocusByUserIdAndGoodsId(goods_id,user_id);

    }

    @Override
    public void deleteFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id) {
        focusMapper.deleteFocusByUserIdAndGoodsId(goods_id,user_id);
    }
}
