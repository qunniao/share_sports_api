package com.gymnasium.store.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.store.Dao.CartDao;
import com.gymnasium.store.PO.CartPO;
import com.gymnasium.store.VO.CartVO;
import com.gymnasium.store.service.CartService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 18:49
 * @Description:
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public Boolean insertCart(CartVO cartVO) {

        CartPO temp = cartDao.findByUserIdAndProductIdAndStyleAndStatus(cartVO.getUserId(), cartVO.getProductId(), cartVO.getStyle(), 1);

        if (temp != null) {

            temp.setUpdateTime(new Date());

            temp.setNumber(temp.getNumber() + cartVO.getNumber());

            cartDao.save(temp);

        } else {

            CartPO cartPO = new CartPO();

            BeanUtils.copyProperties(cartVO, cartPO);

            // 添加状态
            cartPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

            cartPO.setUpdateTime(new Date());

            // 添加创建时间
            cartPO.setCreateTime(new Date());

            cartDao.save(cartPO);
        }
        return true;
    }

    @Override
    public List<CartPO> queryCart(Integer userId, Integer settlementType, String cartId) {

        System.out.println("userId" + userId);
        System.out.println("settlementType" + settlementType);
        System.out.println("cartId" + cartId);


        List<CartPO> cartPOS = cartDao.findAll(new Specification<CartPO>() {
            @Override
            public Predicate toPredicate(Root<CartPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (ObjectUtil.isNotEmpty(userId)) {

                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), userId));
                }

                if (ObjectUtil.isNotEmpty(settlementType)) {

                    list.add(criteriaBuilder.equal(root.get("settlementType").as(Integer.class), settlementType));
                }

                if (ObjectUtil.isNotEmpty(cartId)) {

                    String[] split = StringUtils.split(cartId, ",");

                    Long[] cartIds = (Long[]) ConvertUtils.convert(split, Long.class);

                    Expression<String> exp = root.get("id");

                    list.add(exp.in(cartIds));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        });

        return cartPOS;
    }

    @Override
    public Boolean updateCart(Long id, Integer number) {

        CartPO cartPO = cartDao.findCartPOById(id);

        cartPO.setNumber(number);

        cartPO.setUpdateTime(new Date());

        cartDao.save(cartPO);

        return true;
    }

    @Override
    public Map<String, Object> countCart(Integer userId, Integer settlementType) {

        return cartDao.countCart(userId, settlementType);
    }

    @Override
    public Boolean deleteCart(long[] cartId) {

        return cartDao.deleteCartPO(cartId) > 0;
    }
}
