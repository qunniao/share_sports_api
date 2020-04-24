package com.gymnasium.record.Controller;

import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.record.Dao.CollectDao;
import com.gymnasium.record.PO.CollectPO;
import com.gymnasium.record.Service.RecordService;
import com.gymnasium.record.VO.UserCollectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王志鹏
 * @title: CollectController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 14:57
 */
@Api(value = "收藏API", tags = {"用户测试相关接口"})
@RestController
@RequestMapping(value = "/collect")
public class CollectController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private CollectDao collectDao;

    @ApiOperation(value = "添加收藏", notes = "根据收藏情况添加gymShopId或coachId字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号"),
            @ApiImplicitParam(name = "coachId", value = "教练表主键"),
            @ApiImplicitParam(name = "type", value = "类型:1健身房,2教练", required = true)
    })
    @RequestMapping(value = "/addCollectPO", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> addCollectPO(CollectPO collectPO) {

        return ResultUtil.success(recordService.addCollectPO(collectPO));
    }


    @ApiOperation(value = "查询全部收藏", notes = "根据收藏情况添加gymShopId或coachId字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequestMapping(value = "/queryCollectAll", method = RequestMethod.POST)
    public Result<UserCollectVO> queryCollectAll(CollectPO collectPO) {

        return ResultUtil.success(recordService.queryCollectAll(collectPO));
    }

    @ApiOperation(value = "查询该教练是否收藏过", notes = "根据收藏情况添加gymShopId或coachId字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "coachId", value = "教练表主键", required = true)
    })
    @RequestMapping(value = "/queryByUserIdAndCoachId", method = RequestMethod.POST)
    public Result<CollectPO> queryByUserIdAndCoachId(@RequestParam String userId, @RequestParam int coachId) {
        CollectPO collectPO = collectDao.queryByUserIdAndCoachId(userId, coachId);
        if (collectPO == null) {
            throw new SCException(100458, "没有收藏过!");
        }
        return ResultUtil.success(collectDao.queryByUserIdAndCoachId(userId, coachId));
    }

    @ApiOperation(value = "查询健身房是否收藏过", notes = "根据收藏情况添加gymShopId或coachId字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    })
    @RequestMapping(value = "/queryByUserIdAndGymShopId", method = RequestMethod.POST)
    public Result<CollectPO> queryByUserIdAndGymShopId(@RequestParam String userId, @RequestParam String gymShopId) {

        return ResultUtil.success(collectDao.queryByUserIdAndGymShopId(userId, gymShopId));
    }

    @ApiOperation(value = "删除收藏", notes = "根据删除情况传入gymShopId或coachId字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号"),
            @ApiImplicitParam(name = "coachIds", value = "教练表主键字符串删除多个用字符串分割：1, 2, 3"),
            @ApiImplicitParam(name = "type", value = "类型:1健身房,2教练", required = true)
    })
    @RequestMapping(value = "/deleteCollectPO", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteCollectPO(CollectPO collectPO,String coachIds) {

        int type = collectPO.getType();
        String uerId = collectPO.getUserId();

        if (type == 1) {
            CollectPO collectPO1 = collectDao.queryByUserIdAndGymShopId(uerId, collectPO.getGymShopId());
            if (collectPO1 != null) {
                collectDao.delete(collectPO1);
            }
        } else {

            System.out.println();
            if (StringUtils.isBlank(coachIds)){
                return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
            }
            String[] split = StringUtils.split(coachIds, ",");
            Integer[] coachId = (Integer[]) ConvertUtils.convert(split, Integer.class);

            collectDao.deleteByUserIdAndCoachIdIn(uerId, coachId);
        }

        return ResultUtil.success(true);
    }

}
