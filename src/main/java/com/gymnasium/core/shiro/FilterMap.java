package com.gymnasium.core.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 边书恒
 * @Title: FilterMap
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/28 9:58
 */
public class FilterMap {

    public static Map<String, String> getMap() {

        Map<String, String> filterRuleMap = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断

//        filterRuleMap.put("/test/noPermissionsss", "perms[permission:select]");

        // todo: 功能权限 API
        filterRuleMap.put("/permissions/insert", "perms[permission:insert]");
        filterRuleMap.put("/permissions/update", "perms[permission:update]");
        filterRuleMap.put("/permissions/get", "perms[permission:select]");
        filterRuleMap.put("/permissions/del", "perms[permission:del]");

        // todo:
        // 添加角色权限
        filterRuleMap.put("/rolePermissions/insert", "perms[role:add]");
        filterRuleMap.put("/rolePermissions/del", "perms[role:del]");

        // todo:后台用户API
        filterRuleMap.put("/userManage/queryManageUserByUserId", "perms[user:manage]");
        filterRuleMap.put("/userManage/findAllByRid", "perms[user:manage]");
        filterRuleMap.put("/userManage/addUserManage", "perms[user:manage]");

        // todo 后台相关
        filterRuleMap.put("/adminUser/selectUserManage", "perms[user:manage]");
        filterRuleMap.put("/adminUser/deleteUserManageByUid", "perms[user:manage]");
        filterRuleMap.put("/adminUser/addUserRole", "perms[role:add]");
        filterRuleMap.put("/adminUser/updateUserRole", "perms[role:update]");
        filterRuleMap.put("/adminUser/findAllRole", "perms[role:select]");
        filterRuleMap.put("/adminUser/deleteRoleByRid", "perms[role:del]");
        filterRuleMap.put("/adminUser/addRole", "perms[role:add]");
        filterRuleMap.put("/adminUser/updateRole", "perms[role:update]");

        // todo:后台管理页面列表渲染用户API
        filterRuleMap.put("/modules/findAllModule", "perms[menu:select]");
        filterRuleMap.put("/modules/findMoudelByUsreId", "perms[menu:select]");
        filterRuleMap.put("/modules/updateModuleByRid", "perms[role:update]");
        filterRuleMap.put("/modules/addModule", "perms[menu:add]");
        filterRuleMap.put("/modules/deleteModule", "perms[menu:del]");
        filterRuleMap.put("/modules/updateModule", "perms[menu:update]");
        filterRuleMap.put("/modules/queryAll", "perms[permission:select]");

        // todo: 配置拦截的 优惠券
        // 分页查询我的优惠券
        filterRuleMap.put("/coupon/pageCoupo", "perms[user:select]");
        // 添加优惠券
        filterRuleMap.put("/coupon/addCoupon", "perms[user:add]");
        // 修改优惠卷
        filterRuleMap.put("/coupon/updateCoupon", "perms[user:update]");
        // 下架优惠券
        filterRuleMap.put("/coupon/updateDownCoupon", "perms[user:del]");
        // 删除优惠券
        filterRuleMap.put("/coupon/updateCouponStatus", "perms[user:del]");
        // 人工发放接口
        filterRuleMap.put("/coupon/addCouponOperation", "perms[user:add]");
        // 核销优惠券
        filterRuleMap.put("/coupon/updateCouponOperation", "perms[user:update]");
        // 删除优惠券
        filterRuleMap.put("/coupon/updateDelCouponOperationType", "perms[user:del]");

        // todo: 产品
        // 后台查询产品接口
        filterRuleMap.put("/product/findAll", "perms[user:select]");
        // 修改产品
        filterRuleMap.put("/product/updateProduct", "perms[user:update]");
        // 上架产品
        filterRuleMap.put("/product/inertProduct", "perms[user:add]");
        // 删除产品
        filterRuleMap.put("/product/delProduct", "perms[user:del]");
        // 下架产品
        filterRuleMap.put("/product/deleteProduct", "perms[user:del]");

        // todo: 产品类型

        // 添加产品分类信息
        filterRuleMap.put("/productType/addProductType", "perms[user:del]");
        // 更新产品分类信息
        filterRuleMap.put("/productType/updateProductType", "perms[user:del]");
        // 删除产品分类信息
        filterRuleMap.put("/productType/deleteProductType", "perms[user:del]");

        // todo: 代理
        // 代理人审核
        filterRuleMap.put("/agent/auditAgent", "perms[user:update]");
        // 代理人删除接口
        filterRuleMap.put("/agent/deleteAgent", "perms[user:delete]");
        // 注册一级代理人
        filterRuleMap.put("/agent/insertOneAgent", "perms[user:add]");
        // 代理人列表
        filterRuleMap.put("/agent/pageAllAgent", "perms[user:select]");

        // todo: 代理方案 API
        // 后台代理方案编辑接口
        filterRuleMap.put("/agentScheme/editScheme", "perms[user:update]");
        // 代理方案佣金修改
        filterRuleMap.put("/agent/updateAgentScheme", "perms[user:update]");

        // todo: 渠道
        // 注册一级渠道
        filterRuleMap.put("/channel/insertOneChannel", "perms[user:add]");
        // 编辑渠道接口
        filterRuleMap.put("/channel/auditChannel", "perms[user:update]");
        // 删除渠道人
        filterRuleMap.put("/channel/deleteChannel", "perms[user:del]");
        // 编辑一级渠道方案
        filterRuleMap.put("/channelScheme/editOneLevel", "perms[user:del]");
        // 渠道方案佣金修改
        filterRuleMap.put("/channelScheme/updateChannelScheme", "perms[user:update]");
        // 后台渠道方案编辑
        filterRuleMap.put("/channelScheme/editChannelScheme", "perms[user:update]");

        // todo: 健身房信息
        // 添加
        filterRuleMap.put("/gymshop/addGymShop", "perms[user:add]");
        // 更新健身房
        filterRuleMap.put("/gymshop/updateGymShopByGymShopId", "perms[user:update]");
        // 查询健身房
        filterRuleMap.put("/gymshop/queryGymShopByaAttach", "perms[user:select]");
        // 添加科目
        filterRuleMap.put("/gymshop/addGymSubject", "perms[user:add]");
        //删除健身房
        filterRuleMap.put("/gymshop/delGymShop", "perms[user:del]");

        // todo: 后台用户 菜单
        // 后台相关
        filterRuleMap.put("/adminUser/**", "perms[user:select]");
        // 后台管理页面列表渲染用户API
        filterRuleMap.put("/modules/**", "perms[user:select]");
        // 后台用户API
        filterRuleMap.put("/userManage/**", "perms[user:select]");

        // todo: 文件上传
        // 上传健身房轮播图图片
        filterRuleMap.put("/files/uploadGymImage", "perms[user:add]");

        // 删除健身房轮播图图片
        filterRuleMap.put("/files/deleteGymImage", "perms[user:del]");
        // 删除首页轮播图
        filterRuleMap.put("/Imgs/delScrollimg", "perms[user:del]");
        // 上传(添加)产品轮播图
        filterRuleMap.put("/userManage/uploadProductCarousel", "perms[user:add]");
        //上传(添加)小程序首页轮播图
        filterRuleMap.put("/userManage/uploadScrollimg", "perms[user:add]");

        // todo: 通用数据,如类型等
        // 添加场地设施
        filterRuleMap.put("/datab/addBuilding", "perms[user:add]");
        // 添加科目类型
        filterRuleMap.put("/datab/addSubject", "perms[user:add]");
        //编辑场地
        filterRuleMap.put("/datab/updateDataBuilding", "perms[user:update]");
        // 修改课程
        filterRuleMap.put("/datab/updateDataSubject", "perms[user:update]");

        // todo: 搭伙API
        // 后台查询搭伙记录
        filterRuleMap.put("/partner/queryPartnerRecord", "perms[user:select]");

        // todo: 办理会员/转卡/充值 等订单处理
        // 转卡审核
        filterRuleMap.put("/order/reviewCard", "perms[user:update]");
        // 转卡_更新为退卡,退还用户能量值
        filterRuleMap.put("/order/backCard", "perms[user:update]");
        // 添加卡类型
        filterRuleMap.put("/order/addCardType", "perms[user:add]");
        // 卡状态改为禁用/启用
        filterRuleMap.put("/order/updateCardType", "perms[user:update]");
        // 增加健身房卡
        filterRuleMap.put("/order/addOrderCardType", "perms[user:add]");
        // 删除健身房卡
        filterRuleMap.put("/order/deleteOrderCardType", "perms[user:del]");
        // 修改健身房卡
        filterRuleMap.put("/order/updateOrderCardType", "perms[user:update]");
        // 发货接口
        filterRuleMap.put("/order/shipments", "perms[user:update]");

        // todo: 佣金提现 API
        // 审核提现接口
        filterRuleMap.put("/withdraw/commissionWithdraw", "perms[user:update]");
        // 分页查询用户的提现记录
        filterRuleMap.put("/withdraw/pageUserWithdraw", "perms[user:update]");

        // todo:健身房教练
        // 删除教练
        filterRuleMap.put("/coachuser/deleteCoachUserById", "perms[user:delete]");

        // todo: 微信用户人员信息
        // 删除用户
        filterRuleMap.put("/user/deleteUserByUserId", "perms[user:delete]");
        //实名认证审核接口
        filterRuleMap.put("/user/audit", "perms[user:update]");
        //查询用户实名信息
//        filterRuleMap.put("/user/queryRealNameUser", "perms[user:select]");
        // 分页查询实名认证信息
        filterRuleMap.put("/user/pageRealNameInfo", "perms[user:select]");

        // todo: 商城订单 API
        // 发货
        filterRuleMap.put("/shopOrder/shipments", "perms[user:update]");

        // todo 系统会员卡设置
        // 添加系统会员卡佣金设置
        filterRuleMap.put("/member/addSysMenberCity", "perms[user:add]");
        // 更新会员卡佣金设置
        filterRuleMap.put("/member/updateSysMenberCity", "perms[user:update]");
        // 添加会员信息
        filterRuleMap.put("/member/insertMember", "perms[user:add]");
        // 修改会员信息
        filterRuleMap.put("/member/updateMember", "perms[user:update]");
        // 删除会员信息
        filterRuleMap.put("/member/deleteMember", "perms[user:del]");

        filterRuleMap.put("/**", "anon");
        filterRuleMap.put("/log/**", "anon");
        filterRuleMap.put("/static/**", "anon");
        filterRuleMap.put("/assets/**", "anon");
        filterRuleMap.put("/html/**", "anon");
        filterRuleMap.put("/ds/**", "anon");
        filterRuleMap.put("/images/**", "anon");
        filterRuleMap.put("/js/**", "anon");
        filterRuleMap.put("/system/**", "anon");
        filterRuleMap.put("/views/**", "anon");
        filterRuleMap.put("/index.html", "anon");
        filterRuleMap.put("/login.html", "anon");

        //放行swagger
        filterRuleMap.put("/gymnasium/**", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterRuleMap.put("/configuration/security", "anon");
        filterRuleMap.put("/configuration/ui", "anon");

        filterRuleMap.put("/gymnasium/swagger-ui.html", "anon");
        filterRuleMap.put("/gymnasium/swagger-resources", "anon");
        filterRuleMap.put("/gymnasium/v2/api-docs", "anon");
        filterRuleMap.put("/gymnasium/webjars/springfox-swagger-ui/**", "anon");
        filterRuleMap.put("/gymnasium/configuration/security", "anon");
        filterRuleMap.put("/gymnasium/configuration/ui", "anon");

        filterRuleMap.put("/log/login", "anon");
        filterRuleMap.put("/unauthorized/**", "anon");

//        // 所有请求通过我们自己的JWT Filter
//        filterRuleMap.put("/**", "jwt");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //退出
        filterRuleMap.put("/logout", "logout");

        return  filterRuleMap;
    }

}
