package com.gymnasium.data.Service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Validation;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Dao.CodelogDao;
import com.gymnasium.data.PO.CodelogPO;
import com.gymnasium.data.Service.CodelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: CodelogServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 14:14
 */

@Service
public class CodelogServiceImpl implements CodelogService {

    @Autowired
    private CodelogDao codelogDao;

    @Override
    public String getCode(String phone, Short type) {

        String code = String.valueOf(RandomUtil.getRandom_Six());

        if (!RegexUtil.checkByRegular(Validation.MOBILE, phone)) {
            throw new SCException(ResultEnum.PHONE_NUMBER_ERRROR);
        }

        String response = SMSUtil.send_SMS_Verification_Code(code, phone);

        JSONObject jsonObject = JSON.parseObject(response);
        String errCode = jsonObject.get("Code").toString();

        CodelogPO codelog = new CodelogPO();

        codelog.setCodePhone(phone);
        codelog.setCode(code);
        codelog.setType(type);
        codelog.setCreatTime(DateUtil.getDateToTimestamp(new Date()));
        codelog.setErrlog(errCode);

        if (!errCode.equals("OK")) {
            codelogDao.save(codelog);
            throw new SCException(ResultEnum.ALIBABA_SMS_ERROR);
        }

        codelogDao.save(codelog);
        return code;
    }

    @Override
    public List<CodelogPO> pageCodelog(Paging page, CodelogPO codelogPO) {
        return null;
    }

    @Override
    public CodelogPO queryLastCodelogByPhoen(String phone) {

        return codelogDao.findTop1ByCodePhoneOrderByCodeIdDesc(phone);
    }


    @Override
    public boolean validCode(String phone, String code) throws SCException {

        CodelogPO codelog = codelogDao.findTop1ByCodePhoneOrderByCodeIdDesc(phone);

        Timestamp creatTime = codelog.getCreatTime();
        String codes = codelog.getCode();

        long effectiveDate = 5L * 60L * 1000L;// 设置5分钟有效期
        Date afterDate = new Date(creatTime.getTime() + effectiveDate);

        if (!codes.equals(code)) {
            throw new SCException(ResultEnum.CODE_ERROR);
        }

        return DateUtil.isEffectiveDate(DateUtil.getDateToTimestamp(new Date()), creatTime, afterDate);
    }
}
