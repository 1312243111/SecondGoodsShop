package com.ldu.dao;

import com.ldu.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersMapper {
    /**
     * 查询用户的订单 买
     *
     * @param user_id
     * @return
     */
    public List<Orders> selectOrdersByUserId(Integer user_id);

    /**
     * 查询用户的订单 卖
     *
     * @param user_id
     * @return
     */

    public List<Orders> selectOrdersByUserAndGoods(Integer user_id);
    /**
     * 添加订单
     * @param orders
     */
   public void addOrders(Orders orders);

    /**
     * 发货
     *
     */
    public void deliverByOrderNum(Integer orderNum);

    /**
     * 收货
     * @param orderNum
     */
   public void receiptByOrderNum(Integer orderNum);
    /**
     * 查询所有订单
     * @return
     */

   public List<Orders> getOrdersList();
    /**
     * 根据id获取
     * @param ordersId
     * @return
     */
   public Orders selectById(int ordersId);

    public void updateByPrimaryKey(Orders orders);
    public void deleteByPrimaryKeys(int id);

    public List<Orders> getPageOrdersByOrders(@Param("orderNum")Long orderNum, @Param("orderInformation") String orderInformation, @Param("orderState") Integer orderState);

}
