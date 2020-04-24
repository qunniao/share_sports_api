package com.gymnasium.stadium.Controller;

import com.gymnasium.Util.JwtUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.stadium.Dao.GymShopDao;
import com.gymnasium.stadium.PO.*;
import com.gymnasium.stadium.Service.GymShopService;
import com.gymnasium.stadium.VO.GymFitnessRecordVO;
import com.gymnasium.stadium.VO.GymShopVO;
import com.gymnasium.stadium.VO.GymbsVO;
import com.lly835.bestpay.rest.type.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 王志鹏
 * @title: GymShopController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 15:48
 */

@Api(tags = "健身房 API")
@RestController
@RequestMapping(value = "/gymshop")
public class GymShopController {

    @Autowired
    private GymShopService gymShopService;

    @Autowired
    private GymShopDao gymShopDao;

    @ApiOperation("分页查询,健身房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号"),
            @ApiImplicitParam(name = "gymName", value = "健身房名称"),
            @ApiImplicitParam(name = "gymPhone", value = "健身房电话"),
            @ApiImplicitParam(name = "cityId", value = "地址表主键"),
            @ApiImplicitParam(name = "districtId", value = "区域id"),
            @ApiImplicitParam(name = "longitude", value = "经度"),
            @ApiImplicitParam(name = "latitude", value = "纬度"),
            @ApiImplicitParam(name = "buildingPO.bid", value = "健身房设施"),
            @ApiImplicitParam(name = "gymSubjectPO.sid", value = "健身房设施")
    })
    @RequestMapping(value = "/pageGymShop", method = RequestMethod.POST)
    public Result<Page<GymShopPO>> pageGymShop(int pageNum, int pageSize, @RequestBody @ApiIgnore("GymShopPO") GymShopPO gymShopPO) {
        Paging page = new Paging();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return ResultUtil.success(gymShopService.pageGymShop(page, gymShopPO));
    }

    @ApiOperation("搜索分页查询，健身房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "gymName", value = "健身房名称")
//            @ApiImplicitParam(name = "gymPhone", value = "健身房电话"),
//            @ApiImplicitParam(name = "cityId", value = "地址表主键"),
    })

    @RequestMapping(value = "/searchGymShopBy", method = RequestMethod.POST)
    public Result<Page<GymShopPO>> searchGymShop(Paging page, GymShopPO gymShopPO) {

        return ResultUtil.success(gymShopService.searchGymShop(page, gymShopPO));
    }

    @ApiOperation(value = "分页查询,健身历史记录", notes = "支持根据指定日期查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "startTime", value = "指定日期 时间戳格式", required = false),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = false),
    })
    @RequestMapping(value = "/pageGymFitnessRecord", method = RequestMethod.POST)
    public Result<Page<GymFitnessRecordPO>> pageGymFitnessRecord(Paging page, @ApiIgnore("GymFitnessRecordPO") GymFitnessRecordPO gymFitnessRecordPO) {
        return ResultUtil.success(gymShopService.pageGymFitnessRecord(page, gymFitnessRecordPO));
    }

    @ApiOperation("根据健身编号房查询所有课程和建筑信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    })
    @RequestMapping(value = "/queryGymShopByaAttach", method = RequestMethod.POST)
    public Result<GymShopPO> queryGymShopByaAttach(String gymShopId) {

        return ResultUtil.success(gymShopService.queryGymShopByaAttach(gymShopId));
    }

    @ApiOperation("根据健身房编号查询健身房信息")
    @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    @RequestMapping(value = "/queryByGymShopId", method = RequestMethod.POST)
    public Result<GymbsVO> queryByGymShopId(String gymShopId) {

        return ResultUtil.success(gymShopService.queryByGymShopId(gymShopId));
    }

    @ApiOperation("查询开放匹配的健身房")
    @ApiImplicitParam(name = "districtId", value = "地区编号")
    @RequestMapping(value = "/queryByMatching", method = RequestMethod.POST)
    public Result<GymShopVO> queryByMatching(Integer districtId) {

        return ResultUtil.success(gymShopService.queryByMatching(districtId));
    }

    @ApiOperation("根据健身房编号查询 所属卡信息")
    @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    @RequestMapping(value = "/queryCardTypePOByGymShopId", method = RequestMethod.POST)
    public Result<List<CardTypePO>> queryCardTypePOByGymShopId(@RequestParam("gymShopId") String gymShopId) {

        return ResultUtil.success(gymShopService.queryCardTypePOByGymShopId(gymShopId));
    }

    @ApiOperation("根据健身房编号查询旗下 教练列表")
    @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    @RequestMapping(value = "/queryCoachUserByGymShopId", method = RequestMethod.POST)
    public Result<List<CoachUserPO>> queryCoachUserByGymShopId(@RequestParam("gymShopId") String gymShopId) {

        return ResultUtil.success(gymShopService.queryCoachUserByGymShopId(gymShopId));
    }

    @ApiOperation("分页查询 健身记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true),
            @ApiImplicitParam(name = "type", value = "0未健身,1正在健身,2核销", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
    })
    @RequestMapping(value = "/queryGymFitnessRecordByGymShopId", method = RequestMethod.POST)
    public Result<List<GymFitnessRecordPO>> queryGymFitnessRecordByGymShopId(Paging page, GymFitnessRecordVO gymFitnessRecordVO) {

        return ResultUtil.success(gymShopService.queryGymFitnessRecordByGymShopId(page, gymFitnessRecordVO));
    }

    @ApiOperation("添加健身房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "gymName", value = "健身房名称", required = true),
            @ApiImplicitParam(name = "gymPhone", value = "负责人电话", required = true),
            @ApiImplicitParam(name = "gymEnergy", value = "消耗能量值", required = true),
            @ApiImplicitParam(name = "label", value = "特色标签"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true),
            @ApiImplicitParam(name = "area", value = "面积", required = true),
            @ApiImplicitParam(name = "address", value = "地址", required = true),
            @ApiImplicitParam(name = "cityId", value = "地址表主键", required = true),
            @ApiImplicitParam(name = "districtId", value = "区域id", required = true),
            @ApiImplicitParam(name = "businessTime", value = "营业时间"),
            @ApiImplicitParam(name = "type", value = "0默认,1建设房,2工作室", required = true),
            @ApiImplicitParam(name = "files", value = "上传图片"),
            @ApiImplicitParam(name = "bid", value = "建筑主键", example = "1,2,3"),
            @ApiImplicitParam(name = "sid", value = "科目主键", example = "1,2,3"),
            @ApiImplicitParam(name = "matching", value = "是否开放匹配机制: 0.不开放1.开发"),
            @ApiImplicitParam(name = "preferential", value = "优惠信息"),
    })
    @RequestMapping(value = "/addGymShop", method = RequestMethod.POST)
    public Result<GymShopVO> addGymShop(MultipartFile files, GymShopVO gymShopVO) throws Exception {

        System.out.println(gymShopVO);

        return ResultUtil.success(gymShopService.addGymShop(files, gymShopVO));
    }

    @ApiOperation("更新健身房基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "gid", value = "主键id",required = true),
            @ApiImplicitParam(name = "files", value = "图片"),
            @ApiImplicitParam(name = "gymName", value = "健身房名称"),
            @ApiImplicitParam(name = "gymPhone", value = "负责人电话"),
            @ApiImplicitParam(name = "gymEnergy", value = "消耗能量值"),
            @ApiImplicitParam(name = "label", value = "特色标签"),
            @ApiImplicitParam(name = "longitude", value = "经度"),
            @ApiImplicitParam(name = "latitude", value = "纬度"),
            @ApiImplicitParam(name = "area", value = "面积"),
            @ApiImplicitParam(name = "address", value = "地址"),
            @ApiImplicitParam(name = "cityId", value = "地址表主键"),
            @ApiImplicitParam(name = "districtId", value = "区域id"),
            @ApiImplicitParam(name = "businessTime", value = "营业时间"),
            @ApiImplicitParam(name = "type", value = "0默认,1建设房,2工作室"),
            @ApiImplicitParam(name = "matching", value = "是否开放匹配机制: 0.不开放1.开发"),
            @ApiImplicitParam(name = "preferential", value = "优惠信息"),
    })
    @RequestMapping(value = "/updateGymShopByGymShopId", method = RequestMethod.POST)
    public Result<GymShopVO> updateGymShopByGymShopId(MultipartFile files, GymShopVO gymShopVO, Integer bid) {
        return ResultUtil.success(gymShopService.updateGymShopByGymShopId(files, gymShopVO, bid));
    }

    @ApiOperation("添加健身建筑设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true),
            @ApiImplicitParam(name = "bid", value = "建筑主键", required = true)
    })
    @RequestMapping(value = "/addGymBuilding", method = RequestMethod.POST)
    public Result<GymShopVO> addGymBuilding(@RequestParam("gymShopId") String gymShopId, @RequestParam("bid") Integer bid) {
        return ResultUtil.success(gymShopService.addGymBuilding(gymShopId, bid));
    }

    @ApiOperation("添加健身房课程科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true),
            @ApiImplicitParam(name = "sid", value = "科目主键", required = true)
    })
    @RequestMapping(value = "/addGymSubject", method = RequestMethod.POST)
    public Result<GymShopVO> addGymSubject(@RequestParam("gymShopId") String gymShopId, @RequestParam("sid") Integer sid) {
        return ResultUtil.success(gymShopService.addGymSubject(gymShopId, sid));
    }

    @ApiOperation("开始健身")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true),
            @ApiImplicitParam(name = "userId", value = "人员编号", required = true),
            @ApiImplicitParam(name = "peopleNum", value = "人员数量", required = true),
            @ApiImplicitParam(name = "lat", required = true),
            @ApiImplicitParam(name = "lnt", required = true)
    })
    @RequestMapping(value = "/addStratGym", method = RequestMethod.POST)
    public Result<Boolean> addStratGym(GymFitnessRecordPO gymFitnessRecordPO) {

        return ResultUtil.success(gymShopService.addStratGym(gymFitnessRecordPO));
    }

    @ApiOperation("开始健身扫码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "二维码扫码结果", required = true)
    })
    @RequestMapping(value = "/startGym", method = RequestMethod.POST)
    public Result<GymShopPO> startGym(String code) throws SCException {

        // 解密二维码
        String verify = JwtUtil.verify(code);

        String type = verify.substring(verify.indexOf("type=") + 5,verify.lastIndexOf("&"));

        if (!"1".equals(type)){
            return ResultUtil.error(400601,"不是开始健身的二维码");
        }
        String gymShopId = verify.substring(verify.indexOf("gymShopId=") + 10,verify.indexOf("&"));

        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymShopId);

        return ResultUtil.success(gymShopPO);
    }

    @ApiOperation("结束健身")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "人员编号", required = true),
            @ApiImplicitParam(name = "code", value = "二维码扫码结果", required = true)
    })
    @RequestMapping(value = "/addEndGym", method = RequestMethod.POST)
    public Result<GymFitnessRecordVO> addEndGym(@RequestParam String userId, String code) throws SCException {
        return ResultUtil.success(gymShopService.addEndGym(userId, code));
    }

    @ApiOperation("生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "店铺Id", required = true),
            @ApiImplicitParam(name = "type", value = "类型: 1.开始; 2.结束", required = true)
    })
    @RequestMapping(value = "/getGymShopIdString", method = RequestMethod.POST)
    public Result<String> addEndGym(String gymShopId, Integer type) throws SCException {

        String sign = "gymShopId="+gymShopId+"&type=" + type + "&failure_time="+System.currentTimeMillis()/1000;

        return ResultUtil.success(JwtUtil.sign(sign));
    }

    @ApiOperation("正常核销健身接口")
    @ApiImplicitParam(name = "userId", value = "人员编号", required = true)
    @RequestMapping(value = "/addTowEndGym", method = RequestMethod.POST)
    public Result<GymFitnessRecordVO> addTowEndGym(@RequestParam String userId) throws SCException {
        return ResultUtil.success(gymShopService.addTowEndGym(userId));
    }

    @ApiOperation("用户核销健身接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "人员编号", required = true),
            @ApiImplicitParam(name = "lat", required = true),
            @ApiImplicitParam(name = "lnt", required = true)
    })

    @RequestMapping(value = "/addUserEndGym", method = RequestMethod.POST)
    public Result<GymFitnessRecordVO> addUserEndGym(String userId, String lat, String lnt) throws SCException {
        return ResultUtil.success(gymShopService.addUserEndGym(userId, lat, lnt));
    }
    @ApiOperation("异常核销健身接口")
    @ApiImplicitParam(name = "userId", value = "人员编号", required = true)
    @RequestMapping(value = "/addUnusualTowEndGym", method = RequestMethod.POST)
    public Result<GymFitnessRecordVO> addUnusualTowEndGym(@RequestParam String userId) throws SCException {
        return ResultUtil.success(gymShopService.addUnusualTowEndGym(userId));
    }

    //用预判断是否用户健身状态
    @ApiOperation("查询当前用户健身数据")
    @ApiImplicitParam(name = "userId", value = "人员编号", required = true)
    @RequestMapping(value = "/queryGymFitnessRecordPOByUserId", method = RequestMethod.POST)
    public Result<GymFitnessRecordVO> queryGymFitnessRecordPOBySerialId(@RequestParam String userId) throws SCException {
        return ResultUtil.success(gymShopService.queryGymFitnessRecordPOBySerialId(userId));
    }

    @ApiOperation("查找健身房轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房Id", required = true),
            @ApiImplicitParam(name = "type", value = "类型：0店家轮播图,1教练展示图片")
    })
    @RequestMapping(value = "/findGymImages", method = RequestMethod.POST)
    public Result<Object> findGymImages(Paging page, String gymShopId, Integer type) {

        return gymShopService.findGymImages(page,gymShopId,type);
    }

    //用预判断是否用户健身状态
    @ApiOperation("添加建筑信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bid", value = "建筑主键", required = true, example = "1,2,3"),
            @ApiImplicitParam(name = "gid", value = "健身房id", required = true, example = "1")
    })
    @RequestMapping(value = "/addMoreGymBuilding", method = RequestMethod.POST)
    public Result<Boolean> addMoreGymBuilding(String bid, Integer gid){
        return ResultUtil.success(gymShopService.addMoreGymBuilding(bid, gid));
    }
    @ApiOperation("添加科目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "建筑主键", required = true, example = "1,2,3"),
            @ApiImplicitParam(name = "gid", value = "健身房id", required = true, example = "1")
    })
    @RequestMapping(value = "/addMoreGymSubject", method = RequestMethod.POST)
    public Result<Boolean> addMoreGymSubject(String sid, Integer gid){
        return ResultUtil.success(gymShopService.addMoreGymSubject(sid, gid));
    }
    @ApiOperation("删除建筑信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @RequestMapping(value = "/delGymBuilding", method = RequestMethod.POST)
    public Result<Boolean> delGymBuilding(Integer id){
        return ResultUtil.success(gymShopService.delGymBuilding(id));
    }

    @ApiOperation("删除课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @RequestMapping(value = "/delMoreGymSubject", method = RequestMethod.POST)
    public Result<Boolean> delMoreGymSubject(Integer id){
        return ResultUtil.success(gymShopService.delGymSubject(id));
    }

    @ApiOperation("修改健身建筑设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "健身建筑设施主键", required = true),
            @ApiImplicitParam(name = "bid", value = "建筑主键"),
            @ApiImplicitParam(name = "gid", value = "建筑主键"),
    })
    @RequestMapping(value = "/updateGymBuilding", method = RequestMethod.POST)
    public Result<GymShopVO> updateGymBuilding(GymBuildingPO gymBuildingPO) {
        return ResultUtil.success(gymShopService.updateGymBuilding(gymBuildingPO));
    }

    @ApiOperation("修改健身房课程科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "健身建筑设施主键", required = true),
            @ApiImplicitParam(name = "gid", value = "健身房编号"),
            @ApiImplicitParam(name = "sid", value = "科目主键")
    })
    @RequestMapping(value = "/updateGymSubject", method = RequestMethod.POST)
    public Result<GymShopVO> updateGymSubject(GymSubjectPO gymSubjectPO) {
        return ResultUtil.success(gymShopService.updateGymSubject(gymSubjectPO));
    }

    @ApiOperation("删除健身房")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @PostMapping(value = "/delGymShop")
    public Result<Boolean> delGymShop(Integer id){

        return ResultUtil.success(gymShopService.delGymShop(id));
    }

    @ApiOperation("后台查询健身记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号"),
            @ApiImplicitParam(name = "year", value = "年"),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "day", value = "日"),
    })
    @PostMapping(value = "/queryGymFitnessRecord")
    public Result<Boolean> queryGymFitnessRecord(Paging page, String userId, String gymShopId, @Validated DateVo dateVo){

        return ResultUtil.success(gymShopService.queryGymFitnessRecord(page, userId, gymShopId, dateVo));
    }
}
