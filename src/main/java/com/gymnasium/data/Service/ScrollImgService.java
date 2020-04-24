package com.gymnasium.data.Service;

import com.gymnasium.data.PO.ScrollimgPO;

import java.util.List;

/**
 * @author 边书恒
 * @Title: ScrollImgService
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/25 16:50
 */
public interface ScrollImgService {

    List<ScrollimgPO> findAll(String type);
}
