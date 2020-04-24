package com.gymnasium.store.service;

import com.gymnasium.store.PO.ProductTypePO;
import com.gymnasium.store.VO.ProductTypeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 14:55
 *
 */

public interface ProductTypeService {

    Boolean insertProductType(MultipartFile file, String title, Integer settlementType);

    Boolean updateProductType(MultipartFile file, ProductTypeVO productTypeVO);

    List<ProductTypePO> selectProductType();

    Boolean deleteProductType(Integer id);

}
