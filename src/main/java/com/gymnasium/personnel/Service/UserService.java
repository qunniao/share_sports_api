package com.gymnasium.personnel.Service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.Authentication;
import com.gymnasium.personnel.PO.UserAdditionPO;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.VO.AuthenticationVO;
import com.gymnasium.personnel.VO.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 王志鹏
 * @title: UserManageService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 15:10
 */

public interface UserService {

    Page<UserPO> pageUserPO(Paging page, UserPO userPO);

    boolean addUser(UserVO userVO) throws SCException, APIConnectionException, APIRequestException;

    boolean addUserAddition(UserAdditionPO userAdditionPO);

    boolean updateUserByPhone(UserVO userVO, String code) throws SCException;

    boolean updateUserAddition(UserAdditionPO userAdditionPO) throws SCException;

    boolean updateUser(MultipartFile files, UserVO userVO);

    Boolean uploadAvatar(String userId,String url);

    //修改紧急联系人
    boolean updateCrashUser(UserAdditionPO userAdditionPO);

    UserVO queryUserByUserId(String userId);

    Result deleteUserByUserId(String userId);

    /**
     * 实名认证
     * @param authenticationVO
     * @return
     */
    Boolean certification(AuthenticationVO authenticationVO, MultipartFile files);

    Boolean audit(AuthenticationVO authenticationVO);

    AuthenticationVO queryRealNameUser(Integer userId);

    Page<Authentication> pageRealNameInfo(Paging page);

    Map<String, Object> countUserData(String userId);

    Integer queryUserRole(Integer userId);
}
