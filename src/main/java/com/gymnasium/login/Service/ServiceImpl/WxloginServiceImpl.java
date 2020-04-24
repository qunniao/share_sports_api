package com.gymnasium.login.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.alibaba.fastjson.JSONObject;
import com.gymnasium.Enums.UrlEnums;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.WX.WxUtil;
import com.gymnasium.login.Service.WxloginService;
import com.gymnasium.login.VO.Code2SessionVO;
import com.gymnasium.login.VO.WxloginVO;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.Service.UserService;
import com.gymnasium.personnel.VO.UserVO;
import com.gymnasium.store.service.ServiceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王志鹏
 * @title: WxloginServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/3/28 17:51
 */
@Service
public class WxloginServiceImpl implements WxloginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private ServiceUserService serviceUserService;

    @Autowired
    private JgUtil jgUtil;

    @Override
    public Code2SessionVO get_SessionKeyAndOpenid(String code) throws SCException {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("appid", WxloginVO.Appid);
        params.add("secret", WxloginVO.AppSecret);
        params.add("js_code", code);
        params.add("grant_type", "authorization_code");

        String res = HttpUtil.sendPostRequest(UrlEnums.CODE2SESSION.getUrl(), params);
        JSONObject responseSTR = JSONObject.parseObject(res); //将字符串{“id”：1}

        System.out.println(res);

        Object err = responseSTR.get("errcode");

        if (err != null) {

            throw new SCException(ResultEnum.WX_LOGIN_ERROR);
        }

        String session_key = responseSTR.get("session_key").toString();
        String openid = responseSTR.get("openid").toString();


        Code2SessionVO code2SessionVO = new Code2SessionVO();
        code2SessionVO.setSession_key(session_key);
        code2SessionVO.setOpenid(openid);

        return code2SessionVO;
    }

    @Override
    public Map<String, String> get_AccessToken(String code) {

        Map<String, String> pms = new HashMap<String, String>(4);

        Map<String, String> result = new HashMap<>(5);

        pms.put("appid", WxloginVO.WEB_ID);
        pms.put("secret", WxloginVO.WEB_SECRET);
        pms.put("code", code);
        pms.put("grant_type", "authorization_code");

        String res = OkHttpUtil.get(UrlEnums.GET_ACCESS_TOKEN.getUrl(), pms);

        System.out.println("---get_AccessToken----");
        System.out.println("res:\t" + res);

        JSONObject responseSTR = JSONObject.parseObject(res);
        Object err = responseSTR.get("errcode");

        if (err != null) {
            throw new SCException(ResultEnum.WX_GET_ACCESSTOKEN_ERROR);
        }

        String accessToken = responseSTR.get("access_token").toString();
        String expiresIn = responseSTR.get("expires_in").toString();
        String refreshToken = responseSTR.get("refresh_token").toString();
        String openId = responseSTR.get("openid").toString();
        String scope = responseSTR.get("scope").toString();

        result.put("access_token", accessToken);
        result.put("expires_in", expiresIn);
        result.put("refresh_token", refreshToken);
        result.put("openid", openId);
        result.put("scope", scope);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO loginUserForWX(String code, Integer superiorId) throws SCException, APIConnectionException,
            APIRequestException {

        Map<String, String> result = WxUtil.getSessionKeyAndOpenid(code);

        String openId = result.get("openid");
        String unionId = result.get("unionid");

        UserPO user = userDao.findByUnionId(unionId);
        System.out.println(user);
        if (ObjectUtil.isNull(user)) {
            user = userDao.findByOpenid(openId);
        }

        if (ObjectUtil.isNull(user)) {

            UserVO _userVO = new UserVO();
            _userVO.setOpenid(openId);
            _userVO.setUnionId(unionId);

            userService.addUser(_userVO);

            user = userDao.findByOpenid(openId);

            System.out.println(user);

            // 添加佣金数据
            commissionUtil.addCommission(user.getUid(), superiorId, 1);

            // 分配客服
            Boolean aBoolean = serviceUserService.addServiceUser(user.getUid());

            if (aBoolean) {
                jgUtil.addFriend(user.getUid());
            }
        }

        if (StrUtil.isBlank(user.getOpenid())) {
            user.setOpenid(openId);
        }

        UserVO userVO = new UserVO();

        BeanUtil.copyProperties(user, userVO, Arrays.asList("code2SessionVO", "openid", "shareUid"));

        return userVO;
    }

    @Override
    public UserVO loginUserForGZH(String code, String oneUserId, Integer role) throws SCException, APIConnectionException, APIRequestException {

        Map<String, String> pms = new HashMap<>();

        Map<String, String> result = get_AccessToken(code);

        pms.put("access_token", result.get("access_token"));
        pms.put("openid", result.get("openid"));
        pms.put("lang", "zh_CN");

        String res = OkHttpUtil.get(UrlEnums.GET_USERFOR_WX.getUrl(), pms);
        System.out.println("res: \t" + res);

        JSONObject responseStr = JSONObject.parseObject(res);

        String unionId = responseStr.get("unionid").toString();

        System.out.println("unionid:\t" + unionId);

        UserPO userPO = userDao.findByUnionId(unionId);

        if (ObjectUtil.isEmpty(userPO)) {

            UserPO oneUser = userDao.findByUserId(oneUserId);

            if (ObjectUtil.isEmpty(oneUser)) {
                throw new SCException(45112, "上级id不存在");
            }

            UserVO userVO = new UserVO();
            userVO.setWebid(result.get("openid"));
            userVO.setOneUserId(oneUserId);
            userVO.setUnionId(unionId);
            userVO.setUserNike(responseStr.get("nickname").toString());
            userVO.setTowUsuerId(oneUser.getOneUserId());
            userVO.setSex((int) responseStr.get("sex"));
            userVO.setRole((role - 1) * 2);

            userService.addUser(userVO);

            userPO = userDao.findByUnionId(unionId);

            if ("1".equals(role)) {
                // 添加佣金数据
                commissionUtil.addCommission(userPO.getUid(), oneUser.getUid(), 0);
            }

            // 分配客服
            Boolean aBoolean = serviceUserService.addServiceUser(userPO.getUid());

            if (aBoolean) {
                jgUtil.addFriend(userPO.getUid());
            }
        }

        UserVO userVO = new UserVO();

        BeanUtil.copyProperties(userPO, userVO, Arrays.asList("code2SessionVO", "webid", "unionId"));

        return userVO;
    }


    @Override
    public String getPaidUnionId(WxloginVO wxloginVO) {

        HashMap<String, String> pms = new HashMap<String, String>();
        pms.put("access_token", wxloginVO.getAccess_token());
        pms.put("openid", wxloginVO.getOpenid());


        String res = OkHttpUtil.get(UrlEnums.GET_AUTH_ACCESSTOKEN.getUrl(), pms);

        System.out.println(res);

        JSONObject responseSTR = JSONObject.parseObject(res);
        Object err = responseSTR.get("errcode");

        if (err != null) {
            throw new SCException(ResultEnum.WX_GET_UNIONID_ERROR);
        }

        String unionid = responseSTR.get("unionid").toString();

        return unionid;
    }

}
