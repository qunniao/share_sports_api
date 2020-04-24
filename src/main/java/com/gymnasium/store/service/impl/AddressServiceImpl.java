package com.gymnasium.store.service.impl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.store.Dao.AddressDao;
import com.gymnasium.store.PO.AddressPO;
import com.gymnasium.store.VO.AddressVO;
import com.gymnasium.store.service.AddressService;
import okhttp3.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/21 10:06
 * @Description:
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public AddressPO findAddressBySetDefault(Integer userId) {

        AddressPO address = addressDao.findByUserIdAndIsDefaultAndStatus(userId, 1, 1);

        if (address == null){

            Pageable pageable = PageRequest.of(0,1);

            Page<AddressPO> addressPO = addressDao.findByUserIdOrderByUpdateTimeDesc(userId, pageable);
        }

        return address;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertAddress(AddressVO addressVO) {

        Integer isDefault = addressVO.getIsDefault();
        if (isDefault !=null && isDefault == 1) {

            AddressPO address = addressDao.findByIsDefault(isDefault);

            if (address != null) {
                address.setIsDefault(0);
                addressDao.save(address);
            }
        }

        AddressPO addressPO = new AddressPO();

        // 将前端传过来的VO 赋值给 PO ，存入数据库
        BeanUtils.copyProperties(addressVO, addressPO);

        addressPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        addressPO.setCreateTime(new Date());

        addressPO.setUpdateTime(new Date());

        addressDao.save(addressPO);

        return true;
    }

    @Override
    public List<AddressPO> selectAddress(Integer userId) {

        List<AddressPO> addressPOS = addressDao.findByUserIdAndStatusOrderByUpdateTimeDesc(userId,SysConstant.STATUS_SHOW.getConstant());

        return addressPOS;
    }

    @Override
    public Boolean updateAddress(AddressVO addressVO) {

        Integer isDefault = addressVO.getIsDefault();

        if (isDefault !=null && isDefault == 1) {

            AddressPO address = addressDao.findByIsDefault(isDefault);
            if (address != null) {
                address.setIsDefault(0);
                addressDao.save(address);
            }
        }

        AddressPO addressPO = addressDao.findById((int)addressVO.getId());

        System.out.println(addressPO);

        BeanUtil.copyPropertie(addressVO,addressPO);

        System.out.println(addressPO);

        addressPO.setUpdateTime(new Date());

        addressDao.save(addressPO);

        return true;
    }

    @Override
    public Boolean deleteAddress(Integer id) {

        AddressPO addressPO = addressDao.getOne(id);

        addressPO.setStatus(SysConstant.STATUS_DELETE.getConstant());

        addressDao.save(addressPO);

        return true;
    }
}
