package com.gymnasium.data.Service;

import com.gymnasium.data.PO.CityPO;

import java.util.List;
import java.util.Map;

/**
 * @author 王志鹏
 * @title: CityService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 10:37
 */

public interface CityService {

    List<CityPO> queryAllCity();

    List<CityPO> queryCityByLevel(short level);

    List<CityPO> queryCityByLikeMergerName(String name, short level);

    List<CityPO> queryCityByPid(int pid);

    Map<String, Object> queryCityById(Integer id);
}
