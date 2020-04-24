package com.gymnasium.data.Service.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import com.gymnasium.data.Dao.ScrollimgDao;
import com.gymnasium.data.PO.ScrollimgPO;
import com.gymnasium.data.Service.ScrollImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 边书恒
 * @Title: ScrollImgServiceImpl
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/25 16:51
 */
@Service
public class ScrollImgServiceImpl implements ScrollImgService {

    @Autowired
    private ScrollimgDao scrollimgDao;

    @Override
    public List<ScrollimgPO> findAll(String type) {

        List<ScrollimgPO> scrollImgList = scrollimgDao.findAll(new Specification<ScrollimgPO>() {
            @Override
            public Predicate toPredicate(Root<ScrollimgPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                if (StrUtil.isNotBlank(type)) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        });

        return scrollImgList;
    }
}
