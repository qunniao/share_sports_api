package com.gymnasium.data.Service;

import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.PO.CodelogPO;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CodelogService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 14:14
 */
public interface CodelogService {

    String getCode(String phone, Short type) throws SCException;

    List<CodelogPO> pageCodelog(Paging page, CodelogPO codelogPO);

    CodelogPO queryLastCodelogByPhoen(String phone);

    boolean validCode(String phone, String code) throws SCException;
}
