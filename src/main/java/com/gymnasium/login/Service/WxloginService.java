package com.gymnasium.login.Service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.gymnasium.Util.SCException;
import com.gymnasium.login.VO.AccessTokenVO;
import com.gymnasium.login.VO.Code2SessionVO;
import com.gymnasium.login.VO.WxloginVO;
import com.gymnasium.personnel.VO.UserVO;

import java.util.Map;

/**
 * @author 王志鹏
 * @title: WXlogin
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/3/28 17:49
 */
public interface WxloginService {

    /**
     * 获取会话密钥 和 openId
     * @param code
     * @return
     * @throws SCException
     */
    Code2SessionVO get_SessionKeyAndOpenid(String code) throws SCException;

    Map<String, String> get_AccessToken(String code);

    UserVO loginUserForWX(String code, Integer superiorId) throws SCException, APIConnectionException, APIRequestException;

    UserVO loginUserForGZH(String code, String oneUserId, Integer role) throws SCException, APIConnectionException, APIRequestException;

    String getPaidUnionId(WxloginVO wxloginVO);
}
