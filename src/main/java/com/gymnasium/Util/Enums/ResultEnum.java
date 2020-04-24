package com.gymnasium.Util.Enums;

/**
 * Created by 王志鹏
 * 2017-01-21 14:23
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    NULL_USER(100100, "用户不存在!"),
    USER_REPEAT(100101, "用户不存在!"),
    USER_NOT_NULL(100101, "用户已存在!"),
    USER_PRESENCE(100100, "用户手机号已经存在!"),
    PHONE_NUMBER_ERRROR(100102, "用户手机号已经存在!"),
    PASSWORD_ERROR(100001, "密码错误!"),
    NULL_CODE(100103, "验证码未找到!"),
    CODE_NOT_OK(100104, "请重新获取验证码!"),
    CODE_TIME_OUT(100104, "验证码已过期!"),
    CODE_ERROR(100106, "验证码错误!"),
    PHONE_NUMBER_ERROR(100107, "手机号未通过正则验证"),
    ALIBABA_SMS_ERROR(100108, "阿里短信接口异常,详情请查看日志"),
    WX_LOGIN_ERROR(100109, "微信获取session_key失败"),
    WX_GET_ACCESSTOKEN_ERROR(100110, "微信获取access_token失败"),
    WX_GET_UNIONID_ERROR(100111, "获得unionid失败!"),
    USER_MISMATCH_PRESENCE(100112, "用户电话不匹配!"),
    PRICE_MISMATCH(100113, "会员价格和输入价格不匹配"),
    CARD_NOT_NULL(100114, "卡号已存在"),
    CARD_IS_NULL(100115, "卡号不存在"),
    CARD_TYPE_ERROR(100116, "卡号状态异常"),
    CARD_PASSWORD_ERROR(100119, "激活卡密码错误!"),
    CARD_INSUFFICIENT_BALANCE(100120, "余额不足!"),
    MAONY_ERROR(100121, "必须为5的倍数"),
    LEVER_ERROR(100122, "会员等级判断异常!"),
    CARD_ORDER_NULL(100123, "订单不存在!"),
    CARD_TYPE_IS_ONE(100124, "订单状已激活"),
    CARD_TYPE_IS_ZERO(100125, "订单状已退还"),
    CARD_TYPE_IS_NULL(100126, "当前卡类型不存在!"),
    CARD_NAME_REPEAT(100127, "卡名称不能重复!"),
    TYPE_ERROR(100128, "状态只能为0或1!"),
    NAME_ERROR(100129, "名称已存在"),
    REPEAT_FRIEND(100130, "已经是好友了"),
    CREATE_ACCOUNT_ERROR(100131, "用户名或密码不能为空"),
    LEVEL_ERROR(100132, "健身人数和会员等等级不一致"),
    ENERGY_ERROR(100133, "能量值不够一次健身!"),
    GYM_TOO_ERROR(100134, "不能重复开始健身!当前健身状态为开始!"),
    GYM_hexiao_ERROR(100135, "不能重复开始健身!当前健身状态为开始!"),
    GYM_USER_BEING(100136, "用户正在健身"),
    GYM_USER_NO_BEING(100137, "用户未在健身,不能停止"),
    USER_NOTIN_POOL(100138, "用户未存在搭伙池中,请先填写搭伙级计划!"),
    GYM_USER_NOBEING(100139, "用户未在健身"),
    GYM_QRCode_ERROR(100140, "二维码校验失败"),
    GYM_NULL_ERROR(100141, "健身房不存在"),
    USER_ITEM_IS_NULL(120001, "用户详情资料不存在,请填写个人详细资料"),
    MenberCityPO_IS_NULL(100143, "系统重大异常会员卡为空,请联系管理员"),
    GYM_NULL_ENDTIME(100144, "请先扫码进行结束"),
    DISTANCE_ERROR(100145, "范围错误"),
    QCODE_OUT_TIME(100146, "二维码超时"),
    INCOMPLETE_PARAMETER(100147,"参数不完整"),
    NO_DATA_EXIST(100148,"数据不存在"),
    GRADE_ERROR(100149,"不能开通当前会员等级"),
    SERVICE_IS_BUSY(100150, "客服正忙，请稍等"),
    SERVICE_IS_NULL(100151, "客服不存在"),
    OPENID_IS_NULL(100152, "用户openId不存在")

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
