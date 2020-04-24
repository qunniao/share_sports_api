package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.OrderPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 王志鹏
 * @title: OrderDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 15:25
 */

public interface OrderDao extends JpaRepository<OrderPO, Integer>, JpaSpecificationExecutor<OrderPO> {

    /**
     * Find by order number order po
     *
     * @param orderNumber order number
     * @return the order po
     */
    OrderPO findByOrderNumber(String orderNumber);

    /**
     * 根据id
     *
     * @param id id
     * @return the order po
     */
    OrderPO findOrderPOById(Integer id);

    OrderPO findByIdAndType(Integer id, Integer type);

}