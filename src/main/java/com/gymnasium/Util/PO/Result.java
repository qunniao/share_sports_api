package com.gymnasium.Util.PO;

import lombok.Data;

/**
 * @author 王志鹏
 * @title: Result
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/28 9:33
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;
}
