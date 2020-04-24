package com.gymnasium.file.Controller;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.data.Dao.BuildingDao;
import com.gymnasium.data.Dao.ScrollimgDao;
import com.gymnasium.data.Dao.SubjectDao;
import com.gymnasium.data.PO.BuildingPO;
import com.gymnasium.data.PO.ScrollimgPO;
import com.gymnasium.data.PO.SubjectPO;
import com.gymnasium.file.Dao.ObjFileDao;
import com.gymnasium.file.PO.ObjFilePO;
import com.gymnasium.file.VO.GymImagesVO;
import com.gymnasium.personnel.Service.CoachUserService;
import com.gymnasium.stadium.Dao.CardTypeDao;
import com.gymnasium.stadium.Dao.GymImagesDao;
import com.gymnasium.stadium.PO.CardTypePO;
import com.gymnasium.stadium.PO.GymImagesPO;
import com.gymnasium.stadium.Service.GymShopService;
import com.gymnasium.store.Dao.ProductCarouselDao;
import com.gymnasium.store.PO.ProductCarouselPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: FilesPO
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/25 13:52
 */

@Api(tags = "文件上传,api 所有上传文件接口中含有文件参数为:files 接口中不在描述")
@RestController
@RequestMapping(value = "/files")
public class FilesController {
//文件上传相关代码

    @Autowired
    private CoachUserService coachUserService;

    @Autowired
    private GymShopService gymShopService;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private CardTypeDao cardTypeDao;

    @Autowired
    private ScrollimgDao scrollimgDao;

    @Autowired
    private ObjFileDao objFileDao;

    @Autowired
    private GymImagesDao gymImagesDao;

    @Autowired
    private ProductCarouselDao productCarouselDao;

    @ApiOperation(value = "查询系统文件", notes = "支持名称查询")
    @ApiImplicitParam(name = "name", value = "名称")
    @RequestMapping(value = "/queryObjFilePO", method = RequestMethod.POST)
    public Result<List<ObjFilePO>> queryObjFilePO(ObjFilePO objFilePO){
        List<ObjFilePO> list = new ArrayList<>();
        if (GeneralUtils.notEmpty(objFilePO.getName())) {
            ObjFilePO objFilePO1 = objFileDao.queryByName(objFilePO.getName());
            if (objFilePO1 != null) {
                list.add(objFilePO1);
            }

        } else {
            list = objFileDao.findAll();
        }

        return ResultUtil.success(list);
    }

    @ApiOperation(value = "上传教练图片", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "店家编号", required = true),
            @ApiImplicitParam(name = "url", value = "文件路径", required = true),
            @ApiImplicitParam(name = "userId", value = "教练编号", required = true)
    })
    @RequestMapping(value = "/uploadImagesByCoachUser", method = RequestMethod.POST)
    public Result<GymImagesVO> uploadImagesByCoachUser(@ApiIgnore("GymImagesVO") GymImagesVO gymImagesVO) throws Exception {

        return ResultUtil.success(coachUserService.updateCoachUserAvatar(gymImagesVO));
    }


    @ApiOperation(value = "上传健身房头像图", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "店家编号", required = true),
            @ApiImplicitParam(name = "files", value = "文件路径", required = true)
    })

    @RequestMapping(value = "/uploadGymShopAvatar", method = RequestMethod.POST)
    public Result<Boolean> uploadGymShopAvatar(MultipartFile files, GymImagesVO gymImagesVO) throws Exception {

        return ResultUtil.success(gymShopService.updateGymShopAvatar(files, gymImagesVO));
    }

    @ApiOperation(value = "上传建筑图标", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bid", value = "建筑图标主键", required = true),
            @ApiImplicitParam(name = "xz", value = "1为选中图片,2为不选中", required = true),
            @ApiImplicitParam(name = "url", value = "文件路径", required = true)
    })
    @RequestMapping(value = "/uploadGymBuilding", method = RequestMethod.POST)
    public Result<Boolean> uploadGymBuilding(String url, @RequestParam("bid") int bid, @RequestParam("xz") int xz) throws Exception {

        BuildingPO buildingPO = buildingDao.getOne(bid);

        if (xz == 1) {
            buildingPO.setUrl(url);
        } else {
            buildingPO.setUrly(url);
        }
        buildingDao.save(buildingPO);

        return ResultUtil.success(true);
    }


    @ApiOperation(value = "上传课程图标", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "课程主键", required = true),
            @ApiImplicitParam(name = "xz", value = "1为选中图片,2为不选中", required = true),
            @ApiImplicitParam(name = "url", value = "文件路径", required = true)
    })

    @RequestMapping(value = "/uploadSubject", method = RequestMethod.POST)
    public Result<Boolean> uploadSubject(String url, @RequestParam("sid") int sid, @RequestParam("xz") int xz) throws Exception {

        SubjectPO subjectPO = subjectDao.getOne(sid);

        if (xz == 1) {
            subjectPO.setUrl(url);
        } else {
            subjectPO.setUrly(url);
        }
        subjectDao.save(subjectPO);
        return ResultUtil.success(true);
    }


    @ApiOperation(value = "上传健身房卡图片", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "卡表主键", required = true),
            @ApiImplicitParam(name = "url", value = "文件路径", required = true)
    })
    @RequestMapping(value = "/uploadCardTypePO", method = RequestMethod.POST)
    public Result<Boolean> uploadCardTypePO(String url, @RequestParam int id){


        CardTypePO cardTypePO = cardTypeDao.getOne(id);
        System.out.println(cardTypePO);
        cardTypePO.setUrl(url);
        cardTypeDao.save(cardTypePO);

        return ResultUtil.success(true);
    }

    @ApiOperation(value = "上传健身房轮播图图片", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房Id", required = true),
            @ApiImplicitParam(name = "type", value = "类型", required = true),
            @ApiImplicitParam(name = "files", value = "图片")
    })
    @RequestMapping(value = "/uploadGymImage", method = RequestMethod.POST)
    public Result<Boolean> uploadGymImage(MultipartFile files, GymImagesPO gymImagesPO) {

        String image = FileUtils.uploadImage(files);

        gymImagesPO.setUrl(image);
        gymImagesPO.setStatus(1);
        gymImagesPO.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return ResultUtil.success(ObjectUtils.anyNotNull(gymImagesDao.save(gymImagesPO)));
    }

    @ApiOperation(value = "删除健身房轮播图接口", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @RequestMapping(value = "/deleteGymImage", method = RequestMethod.POST)
    public Result<Boolean> deleteGymImage(Integer id){

        if (StringUtils.isEmpty(id)){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        gymImagesDao.deleteById(id);

        return ResultUtil.success(true);
    }

    @ApiOperation(value = "上传(添加)产品轮播图", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "productId", value = "产品id", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "files", value = "产品轮播图", required = true)
    })
    @RequestMapping(value = "/uploadProductCarousel", method = RequestMethod.POST)
    public Result<Boolean> uploadProductCarousel(List<MultipartFile> files, ProductCarouselPO productCarouselPO) throws Exception {

        // 上传图片
        String image = FileUtils.uploadImage(files);

        String[] images = image.split(",");

        for (String url : images) {

            ProductCarouselPO productCarouse = new ProductCarouselPO();
            BeanUtil.copyPropertie(productCarouselPO, productCarouse);
            productCarouse.setCreateTime(new Date());
            productCarouse.setUrl(url);
            productCarouse.setStatus(SysConstant.STATUS_SHOW.getConstant());
            productCarouselDao.save(productCarouse);
        }

        return ResultUtil.success(true);
    }

    @ApiOperation(value = "上传(添加)小程序首页轮播图", notes = "备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "type", value = "1首页,2社交", required = true),
            @ApiImplicitParam(name = "files", value = "轮播图", required = true)
    })
    @RequestMapping(value = "/uploadScrollimg", method = RequestMethod.POST)
    public Result<Boolean> uploadScrollimg(List<MultipartFile> files, ScrollimgPO scrollimgPO) {

        // 上传图片
        String images = FileUtils.uploadImage(files);

        scrollimgPO.setUrl(images);
        scrollimgPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        scrollimgDao.save(scrollimgPO);
        return ResultUtil.success(true);
    }

    @ApiOperation(value = "上传系统各种文件", notes = "备注")
    @ApiImplicitParam(name = "name", value = "名称", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称(不能重复)", required = true),
            @ApiImplicitParam(name = "remarks", value = "备注", required = false),
            @ApiImplicitParam(name = "url", value = "文件路径", required = true)
    })
    @RequestMapping(value = "/uploadObjFile", method = RequestMethod.POST)
    public Result<Boolean> uploadObjFile(ObjFilePO objFilePO) {
        ObjFilePO objFilePO1 = objFileDao.queryByName(objFilePO.getName());

        if (objFilePO1 != null) {
            throw new SCException(1231, "名称重复");
        }
        objFileDao.save(objFilePO);
        return ResultUtil.success(true);
    }
}