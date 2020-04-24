package com.gymnasium.store.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.FinalEnum;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.file.FastDFSClient;
import com.gymnasium.store.Dao.ProductTypeDao;
import com.gymnasium.store.PO.ProductTypePO;
import com.gymnasium.store.VO.ProductTypeVO;
import com.gymnasium.store.service.ProductTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 15:07
 *
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    public Boolean insertProductType(MultipartFile file, String title, Integer settlementType){

        ProductTypePO productTypePO = new ProductTypePO();

        String url = "";
        try {
            url = FinalEnum.URL_HEAD.getContent() + FastDFSClient.uploadUtil(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (url != "") {

            productTypePO.setImage(url);
        }

        ProductTypePO productType = productTypeDao.findProductTypePOByTitle(title);

        if (productType != null) {
                throw  new SCException(441,"该类型已经存在");
        }

        productTypePO.setSettlementType(settlementType);

        productTypePO.setCreateTime(new Date());

        productTypePO.setUpdateTime(new Date());

        productTypePO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        productTypeDao.save(productTypePO);

        return true;
    }

    @Override
    public Boolean updateProductType(MultipartFile file, ProductTypeVO productTypeVO){

        ProductTypePO productTypePO = productTypeDao.findProductTypePOById(productTypeVO.getId());

        if (ObjectUtil.isNotNull(file)){

            String image = FileUtils.uploadImage(file);
            productTypeVO.setImage(image);
        }

        BeanUtil.copyProperties(productTypeVO, productTypePO);

        System.err.println(productTypePO);

        productTypePO.setUpdateTime(new Date());

        return productTypeDao.save(productTypePO) != null;
    }

    @Override
    public List<ProductTypePO> selectProductType() {


        return productTypeDao.findProductTypePOByStatus(SysConstant.STATUS_SHOW.getConstant());
    }

    @Override
    public Boolean deleteProductType(Integer id) {

        ProductTypePO productType = productTypeDao.getOne(id);

        if (productType == null) {

            throw new SCException(442,"该分类不存在");
        }

        productType.setStatus(SysConstant.STATUS_DELETE.getConstant());

        productTypeDao.save(productType);

        return true;
    }
}
