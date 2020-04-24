package com.gymnasium.order.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.store.VO.ServiceRewardVO;

public interface PayService {

    Result wxPay(String userId, String orderNumber) throws Exception;

    Result orderPay(String userId, String orderNumber) throws Exception;

    Result servicePay(ServiceRewardVO serviceRewardVO)throws Exception;

    String wxPayUnifiedNotify(String notifyXml);

}
