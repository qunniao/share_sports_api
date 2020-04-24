package com.gymnasium.order.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.ActivationCodePO;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.order.VO.EnergyVO;
import com.gymnasium.stadium.PO.CardTypePO;
import com.gymnasium.order.VO.OrderVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 王志鹏
 * @title: OrderService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 15:26
 */
public interface OrderService {

    List<CardTypePO> queryCardTypeByGymShopId(String gymShopId) throws SCException;

    Page<ExpensesRecordPO> pageExpensesRecord(Paging page, ExpensesRecordPO expensesRecordPO, Double minEnergy, Double maxEnergy);

    EnergyVO queryUserEnergyItem(String userId, int type);

    //分页查询转卡订单
    Page<ActivationCodePO> pageEActivationCode(Paging page, ActivationCodePO activationCodePO,
                                               String createDate, String handleDate);

    //搜索查询转卡订单
    List<ActivationCodePO> searchActivationCode(Paging page, ActivationCodePO activationCodePO);

    String createMember(OrderVO orderVO, String code) throws SCException ;

    String energyRecharge(String money, String userId) throws SCException;

    boolean updateActivationCodeType(String activationCode, int type) throws SCException;

    String redemptionCard(ActivationCodePO activationCodePO) throws SCException;

    boolean addCardType(CardTypePO cardTypePO);

    boolean updateCardType(int id, int type);

    /**
     * Update activation code type new
     *
     * @param activationCode
     * @param dockingName
     * @param dockingPhone
     * @return boolean
     */
    boolean updateActivationCodeTypeNew(String activationCode, String dockingName, String dockingPhone);

    boolean reviewCard(ActivationCodePO activationCodePO);

    Result addOrderCardType(CardTypePO cardTypePO);

    Result deleteOrderCardType(int id);

    Result updateOrderCardType(CardTypePO cardTypePO);

    Result findAllOrderCardByGymShop(String gymShopId);

    Page<OrderPO> queryUserOrder(Paging page, OrderVO orderVO, String year, String month);

    Boolean shipments(OrderVO orderVO);

    Boolean receiving(Integer id, String userId);

    /**
     * 会员卡订单分页查询接口
     * @param page
     * @return
     */
    Page<OrderPO> pageMemberCardOrder(Paging page, OrderPO orderPO);

    OrderVO queryDetails(Integer orderId, Integer type);

}
