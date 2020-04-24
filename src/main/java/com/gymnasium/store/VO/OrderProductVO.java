package com.gymnasium.store.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/27 16:37
 * @Description:
 */
@Data
public class OrderProductVO implements Serializable {

    Integer amount;
    Integer totalPrice;
}
