package com.ldu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ldu.dao.GoodsMapper;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ldu.pojo.CommentExtend;
import com.ldu.pojo.Comments;
import com.ldu.pojo.Goods;
import com.ldu.service.GoodsService;
import com.ldu.util.DateUtil;
/**
 * 对商品的操作类（增删改查）
 * @ClassName 	GoodServiceImpl
 * @date		2017-5-9下午9:22:24
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;


    public List<Goods> getAllGoods() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        return goods;
    }



    public List<Goods> getGoodsByCatelogOrderByDate(Integer catelogId,Integer limit) {
        List<Goods> goodsList = goodsMapper.selectByCatelogOrderByDate(catelogId , limit);
        return goodsList;
    }

    @Override
    public List<Goods> getGoodByStr(Integer limit, String name, String describle) {
        List<Goods> goods = goodsMapper.selectByStr(limit, name, describle);
        return goods;
    }


    public List<Goods> getGoodsOrderByDate(Integer limit) {
        List<Goods> goodsList = goodsMapper.selectOrderByDate(limit);
        return goodsList;
    }

    public List<Goods> getGoodsByCatelog(Integer id,String name,String describle) {
        List<Goods> goods = goodsMapper.selectByCatelog(id,name,describle);
        return goods;
    }

    @Override
    public int addGoods(Goods goods, Integer duration) {
        String startTime = DateUtil.getNowDay();
        String endTime = DateUtil.getLastTime(startTime, duration);
        String polishTime = startTime;
        //添加上架时间，下架时间，擦亮时间
        goods.setPolishTime(polishTime);
        goods.setEndTime(endTime);
        goods.setStartTime(startTime);
        return goodsMapper.insert(goods);
    }

    @Override
    public List<Goods> getGoodsByUserId(Integer user_id) {
        List<Goods> goodsList=goodsMapper.getGoodsByUserId(user_id);
        return goodsList;
    }

    public Goods getGoodsByPrimaryKey(Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }

    public void deleteGoodsByPrimaryKey(Integer id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateGoodsByPrimaryKeyWithBLOBs(int goodsId, Goods goods) {
        goods.setId(goodsId);
        this.goodsMapper.updateByPrimaryKeyWithBLOBs(goods);
    }

    @Override
    public CommentExtend selectCommentsByGoodsId(Integer id) {
        return goodsMapper.selectCommentsByGoodsId(id);
    }

    @Override
    public void addComments(Comments comments) {
        goodsMapper.addComments(comments);
    }

    @Override
    public List<Goods> searchGoods(String name, String describle) {
        List<Goods> goods =goodsMapper.searchGoods(name,describle);
        return goods;
    }

    @Override
    public void updateByGoodsId(Goods goods) {
        goodsMapper.updateGoodsByGoodsId(goods);
    }

    @Override
    public Goods getGoodsById(Integer goodsId) {
        Goods goods =goodsMapper.selectById(goodsId);
        return goods;
    }

    @Override
    public int getGoodsNum() {
        List<Goods> goods = goodsMapper.getGoodsList();
        return goods.size();
    }

    @Override
    public List<Goods> getPageGoods(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> list =goodsMapper.getGoodsList();
        return list;
    }

    @Override
    public void deleteGoodsByPrimaryKeys(Integer id) {
        goodsMapper.deleteByPrimaryKeys(id);
    }

    @Override
    public List<Goods> getPageGoodsByGoods(Integer id, String name, Integer status, int pageNum, int pageSize) {
       PageHelper.startPage(pageNum,pageSize);
       List<Goods> list = goodsMapper.getPageGoodsByGoods(id,name,status);
       return list;
    }


}

