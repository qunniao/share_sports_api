package com.gymnasium.store.service;

import com.gymnasium.store.PO.CartPO;
import com.gymnasium.store.VO.CartVO;

import java.util.List;
import java.util.Map;

/**
 * @author 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 18:24
 * @Description:
 */
public interface CartService {

    /**
     * 添加购物车
     * @param cartVO
     * @return
     */
   Boolean insertCart(CartVO cartVO);

    /**
     * 查询购物车
     * @param userId
     * @param settlementType
     * @param cartIds
     * @return
     */
   List<CartPO> queryCart(Integer userId, Integer settlementType, String cartIds);

    /**
     * 更新产品数量
     * @param id
     * @param number
     * @return
     */
    Boolean updateCart(Long id, Integer number);

    /**
     * 统计购物车
     * @param userId
     * @param settlementType
     * @return
     */
    Map<String, Object> countCart(Integer userId, Integer settlementType);

    /**
     * 删除购物车
     * @param cartId
     * @return
     */
    Boolean deleteCart(long[] cartId);

}
