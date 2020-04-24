package com.gymnasium.data.Service.ServiceImpl;

import com.gymnasium.data.Dao.CityDao;
import com.gymnasium.data.PO.CityPO;
import com.gymnasium.data.Service.CityService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王志鹏
 * @title: CityServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 10:39
 */

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;


    @Override
    public List<CityPO> queryAllCity() {
        List<CityPO> cityDaoAll = cityDao.findAll();
        return cityDaoAll;
    }

    @Override
    public List<CityPO> queryCityByLevel(short level) {
        return cityDao.queryByLevel(level);
    }

    @Override
    public List<CityPO> queryCityByLikeMergerName(String name, short level) {
        return cityDao.queryByMergerNameLikeAndLevel("%" + name + "%", level);
    }

    @Override
    public List<CityPO> queryCityByPid(int pid) {
        return cityDao.queryByPid(pid);
    }

    @Override
    public Map<String, Object> queryCityById(Integer id) {

        Map<String, Object> map = new HashMap<>(16);

        // 下级地址信息
        CityPO cityPO = cityDao.queryById(id);

        if (ObjectUtils.anyNotNull(cityPO)){

            Integer pid = cityPO.getPid();

            map.put("superior", pid);

            if (pid != 0){

                CityPO pIdCity = cityDao.queryById(pid);

                map.put("secondLevel", pIdCity.getPid());
            }
        }

        map.put("cityPO", cityPO);

        return map;
    }
}
