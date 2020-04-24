package com.gymnasium.Enums;

/**
 * @author 王志鹏
 * @title: SysConstant
 * @projectName gymnasium
 * @description: 系统常量
 * @date 2019/4/12 9:02
 */

public enum SysConstant {

    STATUS_DISABLE_INT(0, "禁用"),
    STATUS_Enable_INT(1, "启用"),
    TYPE_CARD_ZERO(-1, "退卡"),
    TYPE_CARD_ONE(1, "待激活"),
    TYPE_CARD_TWO(2, "已激活"),
    TYPE_CARD(0, "待审核"),
    TYPE_CONSUMPTION_EXPENDITURE(0, "支出"),
    TYPE_CONSUMPTION_RECHARGE(1, "收入"),
    TYPE_NO_NORMAL(0, "异常"),
    TYPE_IS_NORMAL(1, "正常"),
    TYPE_GYMUSER_NO_START(0, "未健身"),
    TYPE_GYMUSER_RUNING(1, "正在健身"),
    TYPE_GYMUSER_CHECK(2, "等待核销"),
    STATUS_SHOW(1,"展示"),
    STATUS_DELETE(0,"删除"),
    UNIT_RMB(1,"人民币"),
    UNIT_ENERGY(2,"能量值"),
    ORDER_STATUS_CANCEL(0,"取消订单"),
    ORDER_STATUS_PAY(1,"待付款"),
    ORDER_STATUS_DELIVERY(2,"待发货"),
    ORDER_STATUS_RECEIVING(3,"待收货"),
    ORDER_STATUS_DONE(4,"已完成")
    ;

    private int constant;

    private String remarks;

    SysConstant(int constant, String remarks) {
        this.constant = constant;
        this.remarks = remarks;
    }

    public int getConstant() {
        return constant;
    }

    public String getRemarks() {
        return remarks;
    }
}
