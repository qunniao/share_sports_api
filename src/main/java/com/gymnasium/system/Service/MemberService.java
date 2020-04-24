package com.gymnasium.system.Service;

import com.gymnasium.system.PO.SysMemberPO;
import com.gymnasium.system.PO.SysMenberCityPO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 王志鹏
 * @title: MemberService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 17:13
 */
public interface MemberService {

    Boolean addSysMenberCity(SysMenberCityPO sysMenberCityPO);

    SysMenberCityPO queryByCityNameLikeAndMid(String cityName, int mid);

    List<SysMemberPO> querySysMemberAll();

    List<SysMenberCityPO> querySysMenberCityAll();

    Boolean updateSysMenberCity(SysMenberCityPO sysMenberCityPO);

    Boolean insertMember(MultipartFile file, SysMemberPO sysMemberPO) ;

    Boolean updateMember(MultipartFile file, SysMemberPO sysMemberPO) ;

    Boolean deleteMember(Integer id);

    List<SysMemberPO> querySysMember(Integer userId);
}