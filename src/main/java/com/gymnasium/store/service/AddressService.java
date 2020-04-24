package com.gymnasium.store.service;

import com.gymnasium.store.PO.AddressPO;
import com.gymnasium.store.VO.AddressVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/21 10:00
 * @Description:
 */
public interface AddressService {

    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    AddressPO findAddressBySetDefault(Integer userId);

    /**
     * 添加收货地址
     * @param addressVO
     * @return
     */
    Boolean insertAddress(AddressVO addressVO);

    /**
     * 查询收货地址
     * @param userId
     * @return
     */
    List<AddressPO> selectAddress(Integer userId);

    /**
     * 更新收货地址
     * @param addressVO
     * @return
     */
    Boolean updateAddress(AddressVO addressVO);

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    Boolean deleteAddress(Integer id);

}
