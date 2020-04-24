package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.CodelogPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: CodelogDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 14:11
 */
public interface CodelogDao extends JpaRepository<CodelogPO, Integer> {

    CodelogPO findTop1ByCodePhoneOrderByCodeIdDesc(String codePhone);

}
