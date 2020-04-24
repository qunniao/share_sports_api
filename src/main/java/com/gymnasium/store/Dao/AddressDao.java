package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.AddressPO;
import com.gymnasium.store.VO.AddressVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/21 09:58
 * @Description:
 */
public interface AddressDao extends JpaRepository<AddressPO, Integer> {

    /**
     *
     * @param id
     * @return
     */
    AddressPO findById(int id);
    /**
     * 根据用户Id 和 地址状态查询收货地址
     * @param userId
     * @param status
     * @return
     */
    List<AddressPO> findByUserIdAndStatusOrderByUpdateTimeDesc(Integer userId, Integer status);

    /**
     * 根据用户id,地址状态，是否默认，查询地址列表并取出一个最后修改的默认地址。
     * @param userId
     * @param status
     * @return
     */
    AddressPO findByUserIdAndIsDefaultAndStatus(Integer userId, Integer isDefault, Integer status);

    AddressPO findByIsDefault(Integer IsDefault);

    Page<AddressPO> findByUserIdOrderByUpdateTimeDesc(Integer IsDefault, Pageable pageable);
}
