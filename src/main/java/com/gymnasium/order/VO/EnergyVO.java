package com.gymnasium.order.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 王志鹏
 * @title: EnergyVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/29 13:53
 */

@Data
@ApiModel(value = "EnergyVO", description = "能量值")
public class EnergyVO {

    private double sumEnergy;

    private double userEnergy;
}
