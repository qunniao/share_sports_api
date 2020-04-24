package com.gymnasium.order.Controller;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.ActivationCodePO;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.order.Service.ExpensesRecordService;
import com.gymnasium.order.Service.OrderService;
import com.gymnasium.order.VO.EnergyVO;
import com.gymnasium.order.VO.OrderVO;
import com.gymnasium.stadium.PO.CardTypePO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 王志鹏
 * @title: OrderController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/12 16:05
 */

@Api(tags = "办理会员/转卡/充值 等订单处理")
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ExpensesRecordService expensesRecordService;

    @ApiOperation(value = "用户总公共消费之和", notes = "无")
    @RequestMapping(value = "/queryUserEnergyItem", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "type", value = "type:0", required = true)
    })
    public Result<EnergyVO> queryUserEnergyItem(@RequestParam String userId, @RequestParam int type) {
        return ResultUtil.success(orderService.queryUserEnergyItem(userId, type));
    }

    @ApiOperation(value = "分页查询消费记录", notes = "可按类型进行筛选")
    @RequestMapping(value = "/pageExpensesRecord", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "人员编号", required = true),
            @ApiImplicitParam(name = "titleType", value = "1支出,2收入,3冻结,4返还,5扣除", required = true),
            @ApiImplicitParam(name = "itemType", value = "项目类型"),
            @ApiImplicitParam(name = "minEnergy", value = "最小能量值"),
            @ApiImplicitParam(name = "maxEnergy", value = "最大能量值"),
            @ApiImplicitParam(name = "shopId", value = "健身房编号"),
            @ApiImplicitParam(name = "type", value = "类型")
    })
    public Result<Page<ExpensesRecordPO>> pageExpensesRecord(Paging page, ExpensesRecordPO expensesRecordPO,
                                                             Double minEnergy, Double maxEnergy) {

        return ResultUtil.success(orderService.pageExpensesRecord(page, expensesRecordPO, minEnergy, maxEnergy));
    }

    @ApiOperation(value = "分页查转卡订单", notes = "可安类型进行筛选")
    @RequestMapping(value = "/pageEActivationCode", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "人员编号"),
            @ApiImplicitParam(name = "gymShopId", value = "店家编号"),
            @ApiImplicitParam(name = "activationCode", value = "激活码"),
            @ApiImplicitParam(name = "type", value = "0初始,1退卡,2待激活,3已激活"),
            @ApiImplicitParam(name = "reservedName", value = "预留姓名"),
            @ApiImplicitParam(name = "reservedPhone", value = "预留手机号"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", example = "2019-08-13"),
            @ApiImplicitParam(name = "handleDate", value = "办理时间", example = "2019-08-13"),
    })
    public Result<Page<ActivationCodePO>> pageEActivationCode(Paging page, ActivationCodePO activationCodePO,
                                                              String createDate, String handleDate) {
        return ResultUtil.success(orderService.pageEActivationCode(page, activationCodePO,createDate, handleDate));
    }

    @ApiOperation(value = "根据订单id和类型查询会员卡订单详情", notes = "可安类型进行筛选")
    @RequestMapping(value = "/queryDetails", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true),
            @ApiImplicitParam(name = "type", value = "类型 1:开会员2:充值能量值3 购买会员卡", required = true),
    })
    public Result<OrderVO> queryDetails(Integer orderId, Integer type) {
        return ResultUtil.success(orderService.queryDetails(orderId, type));
    }


    @ApiOperation(value = "1.开会员 3.买会员卡", notes = "根据选择的会员等级,创建成功将记录一条订单,修改相应用户资料")
    @RequestMapping(value = "/createMember", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "人员编号", required = true),
            @ApiImplicitParam(name = "name", value = "会员名", required = true),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "level", value = "会员等级", required = true),
            @ApiImplicitParam(name = "price", value = "价格", required = true),
            @ApiImplicitParam(name = "type", value = "1.开会员 3.买会员卡", required = true),
            @ApiImplicitParam(name = "code", value = "验证码5分钟内有效", required = true),
            @ApiImplicitParam(name = "number", value = "购买会员卡的数量,购买单个时可为空"),
            @ApiImplicitParam(name = "address", value = "购买未激活的会员卡的地址,为空")
    })
    public Result<String> createMember(OrderVO orderVO, @RequestParam("code") String code) {

        return ResultUtil.success(orderService.createMember(orderVO, code));
    }

    @ApiOperation(value = "充值能量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "money", value = "金额", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })    @RequestMapping(value = "/energyRecharge", method = RequestMethod.POST)

    public Result<String> energyRecharge(@RequestParam("money") String money, @RequestParam("userId") String userId) {

        return ResultUtil.success(orderService.energyRecharge(money, userId));
    }

    @ApiOperation(value = "转卡_兑换卡,生成订单和兑换码,当前状态为待激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardTypeId", value = "卡片类型主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "reservedName", value = "预留姓名", required = true),
            @ApiImplicitParam(name = "reservedPhone", value = "预留手机号", required = true)
    })
    @RequestMapping(value = "/redemptionCard", method = RequestMethod.POST)
    public Result<String> redemptionCard(ActivationCodePO activationCodePO) {
        return ResultUtil.success(orderService.redemptionCard(activationCodePO));
    }

    @ApiOperation(value = "转卡_更新状态为以激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activationCode", value = "激活码", required = true),
            @ApiImplicitParam(name = "dockingName", value = "对接人姓名", required = true),
            @ApiImplicitParam(name = "dockingPhone", value = "电话", required = true)
    })
    @RequestMapping(value = "/updateActivationCodeType", method = RequestMethod.POST)
    public Result<ActivationCodePO> updateActivationCodeType(String activationCode, String dockingName, String dockingPhone) {

        return ResultUtil.success(orderService.updateActivationCodeTypeNew(activationCode,  dockingName, dockingPhone));
    }

    @ApiOperation(value = "转卡审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "订单号", required = true),
            @ApiImplicitParam(name = "reviewState", value = "审核状态 1.审核通过2.拒绝", required = true)
    })
    @RequestMapping(value = "/reviewCard", method = RequestMethod.POST)
    public Result<ActivationCodePO> reviewCard(ActivationCodePO activationCode) {

        return ResultUtil.success(orderService.reviewCard(activationCode));
    }

    @ApiOperation(value = "转卡_更新为退卡,退还用户能量值")
    @ApiImplicitParam(name = "activationCode", value = "激活码", required = true)
    @RequestMapping(value = "/backCard", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result<ActivationCodePO> backCard(@RequestParam("activationCode") String activationCode) throws SCException {

        return ResultUtil.success(orderService.updateActivationCodeType(activationCode, SysConstant.TYPE_CARD_ZERO.getConstant()));
    }

    @ApiOperation(value = "添加卡类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "店家编号", required = true),
            @ApiImplicitParam(name = "energy", value = "消耗能量值", required = true),
            @ApiImplicitParam(name = "name", value = "卡片名称", required = true),
            @ApiImplicitParam(name = "servicePhone", value = "客服热线", required = true),
            @ApiImplicitParam(name = "workNumber", value = "客服工号", required = true)
    })
    @RequestMapping(value = "/addCardType", method = RequestMethod.POST)
    public Result<CardTypePO> addCardType(CardTypePO cardTypePO) throws SCException {

        return ResultUtil.success(orderService.addCardType(cardTypePO));
    }

    @ApiOperation(value = "卡状态改为禁用/启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "卡片类型主键", required = true),
            @ApiImplicitParam(name = "type", value = "0无效1有效", required = true)
    })
    @RequestMapping(value = "/updateCardType", method = RequestMethod.POST)
    public Result<CardTypePO> updateCardType(@RequestParam("id") int id, @RequestParam("type") int type) throws SCException {

        return ResultUtil.success(orderService.updateCardType(id, type));
    }


    @ApiOperation(value = "统计能量值", notes = "可安类型进行筛选")
    @RequestMapping(value = "/statisticalEnergy", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编码, String的userId 非uid", required = true)
    })
    public Result<Map<String, Object>> statisticalEnergy(String userId) {

        return ResultUtil.success(expensesRecordService.statisticalEnergy(userId));
    }

    @ApiOperation(value = "增加健身房卡", notes = "")
    @RequestMapping(value = "/addOrderCardType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "energy", value = "能量值", required = true),
            @ApiImplicitParam(name = "url", value = "路径", required = true),
            @ApiImplicitParam(name = "marketPrice", value = "市场价格", required = true),
            @ApiImplicitParam(name = "remakes", required = true),
            @ApiImplicitParam(name = "servicePhone", value = "服务热线", required = true),
            @ApiImplicitParam(name = "workNumber", value = "客服工号", required = true)
    })
    public Result addOrderCardType(CardTypePO cardTypePO) {
        return orderService.addOrderCardType(cardTypePO);
    }


    @ApiOperation(value = "删除健身房卡", notes = "改变status的状态")
    @RequestMapping(value = "/deleteOrderCardType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "健身房卡Id", required = true)
    })
    public Result deleteOrderCardType(int id) {
        return orderService.deleteOrderCardType(id);
    }

    @ApiOperation(value = "修改健身房卡", notes = "")
    @RequestMapping(value = "/updateOrderCardType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "健身房卡Id", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号"),
            @ApiImplicitParam(name = "name", value = "名称"),
            @ApiImplicitParam(name = "energy", value = "能量值", required = true),
            @ApiImplicitParam(name = "url", value = "路径"),
            @ApiImplicitParam(name = "marketPrice", value = "市场价格"),
            @ApiImplicitParam(name = "remakes"),
            @ApiImplicitParam(name = "servicePhone", value = "服务热线"),
            @ApiImplicitParam(name = "workNumber", value = "客服工号")
    })
    public Result updateOrderCardType(CardTypePO cardTypePO) {
        return orderService.updateOrderCardType(cardTypePO);
    }

    @ApiOperation(value = "根据健身房Id查找健身房卡")
    @RequestMapping(value = "/findAllOrderCardByGymShop", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房Id", required = true)
    })
    public Result findAllOrderCardByGymShop(String gymShopId) {
        return orderService.findAllOrderCardByGymShop(gymShopId);
    }

    @ApiOperation(value = "用户会员卡订单查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "用户编号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "用户编号", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "orderState", value = "订单状态"),
            @ApiImplicitParam(name = "year", value = "年份"),
            @ApiImplicitParam(name = "month", value = "月份不为空时，必须传年份")
    })
    @RequestMapping(value = "/queryUserCardOrder", method = RequestMethod.POST)
    public Result<Page<OrderPO>> queryUserCardOrder(Paging page, OrderVO orderVO, String year, String month){

        return ResultUtil.success(orderService.queryUserOrder(page, orderVO, year, month));
    }

    @ApiOperation(value = "会员卡订单分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "memberPrice", value = "会员卡价格"),
            @ApiImplicitParam(name = "level", value = "等级"),
            @ApiImplicitParam(name = "orderState", value = "订单状态"),
            @ApiImplicitParam(name = "type", value = "类型"),
    })
    @RequestMapping(value = "/pageMemberCardOrder", method = RequestMethod.POST)
    public Result<Page<OrderPO>> pageMemberCardOrder(Paging page,OrderPO orderPO){

        return ResultUtil.success(orderService.pageMemberCardOrder(page, orderPO));
    }

    @ApiOperation("发货接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true),
            @ApiImplicitParam(name = "deliveryWay", value = "配送方式", required
                    = true),
            @ApiImplicitParam(name = "courierNumber", value = "快递单号",
                    required = true)
    })
    @RequestMapping(value = "/shipments", method = RequestMethod.POST)
    public Result<Boolean> shipments(OrderVO orderVO) {

        return ResultUtil.success(orderService.shipments(orderVO));
    }

    @ApiOperation("收货接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/receiving", method = RequestMethod.POST)
    public Result<Boolean> receiving(Integer id, String userId) {

        return ResultUtil.success(orderService.receiving(id, userId));
    }
}
