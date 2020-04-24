package com.gymnasium.Util.vo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 边书恒
 * @Title: DateVo
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/23 10:49
 */
@Data
public class DateVo {

    private Integer year;

    @Max(value = 12, message = "月份不能大于12")
    @Min(value = 1, message = "月份不能小于1")
    private Integer month;
    @Max(value = 31, message = "天数不能大于31")
    @Min(value = 1, message = "天数不能小于1")
    private Integer day;

    public String formatDate() {

        StringBuffer date = new StringBuffer();

        boolean yearNotNull = ObjectUtil.isNotNull(year);
        boolean monthNotNull = ObjectUtil.isNotNull(month);
        boolean dayNotNull = ObjectUtil.isNotNull(day);

        if (yearNotNull) {
            date.append(year);
        }

        if (yearNotNull && monthNotNull) {

            if (month < 10) {
                date.append("-0");
            } else {
                date.append("-");
            }
            date.append(month);
        }

        if (yearNotNull && monthNotNull && dayNotNull) {

            if (day < 10) {
                date.append("-0");
            } else {
                date.append("-");
            }
            date.append(day);
        }

        return date.toString();
    }
}
