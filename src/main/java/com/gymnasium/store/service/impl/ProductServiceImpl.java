package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.ProductCarouselDao;
import com.gymnasium.store.Dao.ProductDao;
import com.gymnasium.store.PO.ProductCarouselPO;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.VO.ProductVO;
import com.gymnasium.store.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/17 18:58
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCarouselDao productCarouselDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertProduct(MultipartFile file, List<MultipartFile> details, List<MultipartFile> carouselFiles, ProductVO productVO) {

        System.err.println(productVO);
        ProductPO productPO = new ProductPO();

        if (file != null) {
            String url = FileUtils.uploadImage(file);

            productVO.setImage(url);
        }
        if (ObjectUtil.isNotEmpty(details) && details.size() > 0) {
            String url = FileUtils.uploadImage(details);
            productVO.setDetails(url);
        }

        BeanUtil.copyProperties(productVO, productPO);

        Integer putaWayStatus = 1;
        // 设置状态
        if (productVO.getProductTypeId() == 5) {
            putaWayStatus = 2;
        }
        productPO.setPutawayStatus(putaWayStatus);

        System.err.println(productPO);

        Integer unitValue = productPO.getSettlementType() == 1 ? 1 : 2;

        productPO.setUnit(unitValue);

        productPO.setCreateTime(new Date());

        String productNumber = RandomUtil.randomString(7);

        productPO.setProductNumber(productNumber);

        productDao.save(productPO);

        // 上传产品轮播图
        String carouselStr = FileUtils.uploadImage(carouselFiles);

        productCarouselUp(carouselStr, productPO);

        return productPO.getId();
    }

    @Override
    public Boolean updateProduct(MultipartFile file, List<MultipartFile> details, List<MultipartFile> carouselFiles, ProductVO productVO) {

        ProductPO productPO = productDao.getOne(productVO.getId());

        if (ObjectUtil.isNull(productPO)) {
            throw new SCException(400751, "产品不存在");
        }

        if (ObjectUtil.isNotNull(file)) {
            String url = FileUtils.uploadImage(file);
            productVO.setImage(url);
        }
        if (ObjectUtil.isNotEmpty(details)) {
            String url = FileUtils.uploadImage(details);
            productVO.setDetails(url);
        }

        if (ObjectUtil.isNotEmpty(carouselFiles)) {
            String url = FileUtils.uploadImage(carouselFiles);
            productCarouselUp(url, productPO);
        }

        BeanUtil.copyProperties(productVO, productPO);

        return ObjectUtil.isNotNull(productDao.save(productPO));
    }

    @Override
    public Boolean deleteProduct(Integer id) {

        ProductPO product = productDao.getOne(id);

        if (ObjectUtil.isNull(product)) {
            throw new SCException(400751, "产品不存在");
        }

        product.setPutawayStatus(SysConstant.STATUS_DELETE.getConstant());

        return ObjectUtil.isNotNull(productDao.save(product));
    }

    @Override
    public Page<ProductPO> pageAll(Paging page, ProductVO productVO, String sorts) {

        Sort sort;

        if ("1".equals(sorts)) {

            sort = new Sort(Sort.Direction.ASC, "showPrice");
        } else if ("2".equals(sorts)) {

            sort = new Sort(Sort.Direction.DESC, "salesVolume");
        } else {

            sort = new Sort(Sort.Direction.ASC, "id");
        }

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), sort);

        Page<ProductPO> product = productDao.findAll(new Specification<ProductPO>() {

            @Override
            public Predicate toPredicate(Root<ProductPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();
                if (GeneralUtils.notEmpty(productVO.getProductTypeId())) {
                    list.add(criteriaBuilder.equal(root.get("productTypeId").as(Integer.class), productVO.getProductTypeId()));
                }

                if (GeneralUtils.notEmpty(productVO.getSettlementType())) {
                    list.add(criteriaBuilder.equal(root.get("settlementType").as(Integer.class), productVO.getSettlementType()));
                }

                if (GeneralUtils.notEmpty(productVO.getName())) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + productVO.getName() + "%"));
                }

                if (GeneralUtils.notEmpty(productVO.getRecommend())) {
                    list.add(criteriaBuilder.equal(root.get("recommend").as(Integer.class), productVO.getRecommend()));
                }

                System.out.println("getPutawayStatus" + productVO.getPutawayStatus());

                if (ObjectUtil.isNotNull(productVO.getPutawayStatus())) {

                    list.add(criteriaBuilder.equal(root.get("putawayStatus").as(Integer.class), productVO.getPutawayStatus()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return product;
    }

    @Override
    public ProductVO findById(Integer id) {

        ProductVO productVO = new ProductVO();

        ProductPO productPO = productDao.findProductPOById(id);

        System.out.println(productPO);

        if (productPO == null) {
            return null;
        }

        BeanUtils.copyProperties(productPO, productVO);

        return productVO;
    }

    @Override
    public Boolean delProduct(Integer id) {

        ProductPO product = productDao.getOne(id);

        if (ObjectUtil.isNull(product)) {
            throw new SCException(400751, "产品不存在");
        }

        productDao.deleteById(id);

        return true;
    }

    private Boolean productCarouselUp(String carouselStr, ProductPO productPO) {

        if (StrUtil.isBlank(carouselStr)) {
            return Boolean.FALSE;
        }

        String[] images = carouselStr.split(",");

        List<ProductCarouselPO> productCarouselPOList = new LinkedList<>();

        for (String url : images) {

            ProductCarouselPO productCarouse = new ProductCarouselPO();
            productCarouse.setProductId(productPO.getId());
            productCarouse.setName(productPO.getName());
            productCarouse.setCreateTime(new Date());
            productCarouse.setUrl(url);
            productCarouse.setStatus(SysConstant.STATUS_SHOW.getConstant());

            productCarouselPOList.add(productCarouse);
        }

        productCarouselDao.saveAll(productCarouselPOList);

        return Boolean.TRUE;

    }
}
