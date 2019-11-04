package com.ldu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ldu.pojo.CommentExtend;
import com.ldu.pojo.Comments;
import com.ldu.pojo.Goods;

public interface GoodsMapper {

    /**
     * 添加物品
     * @param record
     * @return
     */
    int insert(Goods record);


    public List<Goods> selectAllGoods();


    public List<Goods> selectByCatelogOrderByDate(@Param("catelogId") Integer catelogId, @Param("limit") Integer limit);


    /**
     * 查询最新发布商品信息，结果按擦亮时间排序，最新的在前
     *
     * @return
     */
    public List<Goods> selectOrderByDate(@Param("limit") Integer limit);


    /**
     * 根据最新发布分类，查询商品
     * @param
     * @return
     */
    public List<Goods> selectByStr(@Param("limit")Integer limit,@Param("name") String name,@Param("describle") String describle);

    /**
     * 根据商品分类的id，查询商品
     * @param catelog_id
     * @return
     */
    public List<Goods> selectByCatelog(@Param("catelog_id") Integer catelog_id,@Param("name") String name,@Param("describle") String describle);

    /**
     * 查询登录用户的所有闲置商品
     * @param user_id
     * @return
     */
    public List<Goods> getGoodsByUserId(Integer user_id);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    Goods selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);//更新
    /**
     * 通过主键更改信息，
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBs(Goods record);

    CommentExtend selectCommentsByGoodsId(@Param("id")Integer id);

   public void addComments(Comments comments);

    List<Goods> searchGoods(@Param("name") String name,@Param("describle") String describle);

    int updateGoodsByGoodsId(Goods goods);

    Goods selectById(Integer goodsId);

    List<Goods> getGoodsList();

    int  deleteByPrimaryKeys(Integer id);

    /**
     * 模糊查询
     * @param id
     * @param name
     * @param status
     * @return
     */
    List<Goods> getPageGoodsByGoods(@Param("id") Integer id,@Param("name") String name,@Param("status") Integer status);
}