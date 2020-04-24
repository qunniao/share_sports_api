package com.gymnasium.order.Service.ServiceImpl;

import com.gymnasium.order.DAO.ExpensesRecordDao;
import com.gymnasium.order.Service.ExpensesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/12 13:35
 * @Description:
 */
@Service
public class ExpensesRecordServiceImpl implements ExpensesRecordService {

    @Autowired
    private ExpensesRecordDao expensesRecordDao;


    @Override
    public Map<String, Object> statisticalEnergy(String userId) {

        Map<String, Object> map = new HashMap<>(16);


        //统计用户消费的总能量值
        double cumulativeEnergy = expensesRecordDao.queryCumulativeEnergy(userId, 1);

        // 统计用户冻结的总能量值
        double freezingEnergy  = expensesRecordDao.queryCumulativeEnergy(userId, 3);

        //查询用户余额
        double energy = expensesRecordDao.queryEnergy(userId);

        map.put("累计能量值使用",cumulativeEnergy);
        map.put("剩余能量值(余额)",energy);
        map.put("冻结能量值",freezingEnergy);

        return map;
    }
}
