package com.gymnasium.data.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.data.Dao.ScrollimgDao;
import com.gymnasium.data.PO.ScrollimgPO;
import com.gymnasium.data.Service.ScrollImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author 王志鹏
 * @title: ImgController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/30 9:45
 */


@Api(tags = "小程序首页轮播图")
@RestController
@RequestMapping(value = "/Imgs")
public class ImgController {
    //查询首页轮播图
    //删除首页轮播图


    @Autowired
    private ScrollImgService scrollImgService;

    @Autowired
    private ScrollimgDao scrollimgDao;

    @ApiOperation("查询首页轮播图")
    @ApiImplicitParam(name = "type", value = "类型")
    @RequestMapping(value = "/queryScrollImgAll", method = RequestMethod.POST)
    public Result<List<ScrollimgPO>> queryScrollImgAll(String type) {

        return ResultUtil.success(scrollImgService.findAll(type));
    }

    @ApiOperation("删除首页轮播图")
    @RequestMapping(value = "/delScrollimg", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delScrollimg(@RequestParam Integer id) {


        ScrollimgPO scrollimgPO = scrollimgDao.queryById(id);

        if (scrollimgPO == null) {
            throw new SCException(111, "删除失败,未找到该图片!");
        }

        scrollimgDao.delete(scrollimgPO);

        return ResultUtil.success(true);
    }

}
