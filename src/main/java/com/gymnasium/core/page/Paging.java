package com.gymnasium.core.page;


import lombok.Data;

/**
 * @author 王志鹏
 * @title: Pageing
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/1916:42
 */
@Data
public class Paging {

    private int pageNum = 1;

    private int pageSize =15;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
