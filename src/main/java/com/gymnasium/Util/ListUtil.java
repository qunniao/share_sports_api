package com.gymnasium.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王志鹏
 * @title: ListUtil
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/15 19:58
 */
public class ListUtil {
    public static <E> List<E> transferArrayToList(E[] array) {

        List<E> transferedList = new ArrayList<>();
        Arrays.stream(array).forEach(arr -> transferedList.add(arr));

        return transferedList;
    }
}
