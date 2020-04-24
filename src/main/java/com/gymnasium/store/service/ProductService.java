package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.VO.ProductVO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    /**
     * 添加产品信息
     * @param file
     * @param productVO
     * @return
     */
    Integer insertProduct(MultipartFile file, List<MultipartFile> details, List<MultipartFile> carouselFiles,
                          ProductVO productVO);

    Boolean updateProduct(MultipartFile file, List<MultipartFile> details, List<MultipartFile> carouselFiles,
                          ProductVO productVO);

    Boolean deleteProduct(Integer id);

    Page<ProductPO> pageAll(Paging page, ProductVO productVO, String sorts);

    ProductVO findById(Integer productId);

    Boolean delProduct(Integer id);

}
