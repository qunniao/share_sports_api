package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ProductCarouselPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 14:56
 * @Description:
 */
public interface ProductCarouselDao extends JpaRepository<ProductCarouselPO, Integer> {

    /**
     * 根据产品id 查询所有轮播图
     * @param productId
     * @param status
     * @return
     */
    List<ProductCarouselPO> findAllByProductIdAndStatus(Integer productId, Integer status);

    ProductCarouselPO findProductCarouselPOById(Integer id);
}
