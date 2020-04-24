package com.gymnasium.store.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/23 11:31
 * @Description:
 */
@Data
public class ProductInfoVO implements Serializable {

    private Integer productId;

    private Integer number;

    private String style;
}
