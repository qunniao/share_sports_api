package com.gymnasium.stadium.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: GymbsPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 15:23
 */
@Data
public class GymbsVO<T> implements Serializable {

    private T data;
}
