package com.gymnasium.order.PO;

import com.gymnasium.Util.DateUtil;
import com.gymnasium.Util.RandomUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 王志鹏
 * @title: ExpensesRecord
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/16 10:25
 */
@Data
@Entity
@Table(name = "order_user_expensesrecord", catalog = "gymnasium")
@ApiModel(value = "ExpensesRecordPO", description = "用户消费记录")
public class ExpensesRecordPO implements Serializable {

    public static final int Record_type_pay = 1;
    public static final int Record_type_income = 2;
    public static final int Record_type_freeze = 3;
    public static final int Record_type_Return = 4;
    public static final int Record_type_deduct = 5;

    public static final int Record_type_outpay = 0;//支出
    public static final int Record_type_revenue = 1;//收入


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;

    @Column(name = "uexId")
    @ApiModelProperty(value = "操作流水号", name = "uexId", example = "asfda")
    private String uexId;

    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    @Column(name = "userId")
    private String userId;

    @ApiModelProperty(value = "能量值", name = "energy", example = "1")
    @Column(name = "energy")
    private Double energy;

    @Column(name = "type")
    @ApiModelProperty(value = "0支出,1收入", name = "type", example = "1")
    private Integer type;

    //后三个是搭伙用的
    @Column(name = "titleType")
    @ApiModelProperty(value = "类型:1支出,2收入,3冻结,4返还,5扣除", name = "titleType", example = "1")
    private Integer titleType;

    @Column(name = "surplusPrice")
    @ApiModelProperty(value = "余额480,-20,500", name = "SurplusPrice", example = "480")
    private Double surplusPrice;

    @Column(name = "caption")
    @ApiModelProperty(value = "说明", name = "caption", example = "1")
    private String caption;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注", name = "remarks", example = "1")
    private String remarks;

    @Column(name = "operatingTime")
    @ApiModelProperty(value = "操作时间", name = "operatingTime", example = "1")
    private Timestamp operatingTime;

    @Column(name = "shop_id")
    @ApiModelProperty(value = "健身房id", name = "shopId", example = "1")
    private String shopId;

    @Column(name = "item_type")
    @ApiModelProperty(value = "项目类型:1.开会员2.充值能量值2.健身3.健身房转卡4.搭伙抵押5.搭伙抵押6.购买商品", name = "itemType", example = "1")
    private Integer itemType;


    public static ExpensesRecordPO createRecord(String userId, double energy, double surplusPrice, int titleType,
                                                int type, String caption, String remarks) {
        ExpensesRecordPO expensesRecordPO = new ExpensesRecordPO();
        expensesRecordPO.setUexId("UEX_" + RandomUtil.getShortUuid() + DateUtil.current("yyMMddHHmmss"));
        expensesRecordPO.setUserId(userId);
        expensesRecordPO.setEnergy(energy);
        expensesRecordPO.setSurplusPrice(surplusPrice);
        expensesRecordPO.setTitleType(titleType);
        expensesRecordPO.setType(type);
        expensesRecordPO.setCaption(caption);
        expensesRecordPO.setRemarks(remarks);
        expensesRecordPO.setOperatingTime(DateUtil.getDateToTimestamp(new Date()));
        return expensesRecordPO;
    }

}
