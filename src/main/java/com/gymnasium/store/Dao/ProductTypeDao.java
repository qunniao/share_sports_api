package com.gymnasium.store.Dao;
import com.gymnasium.store.PO.ProductTypePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/17 19:23
 *
 */
public interface ProductTypeDao extends JpaRepository<ProductTypePO, Integer> {

    /**
     * 根据分类名称查找产品类型
     * @param title
     * @return
     */
    ProductTypePO findProductTypePOByTitle(String title);

    /**
     * 查找产品类型列表
     * @param status
     * @return
     */
    List<ProductTypePO> findProductTypePOByStatus(Integer status);

    ProductTypePO findProductTypePOById(Integer id);
}
