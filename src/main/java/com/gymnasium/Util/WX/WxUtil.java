package com.gymnasium.Util.WX;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.gymnasium.Enums.UrlEnums;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.HttpUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.login.VO.WxloginVO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 边书恒
 * @Title: WxUtil
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/22 18:42
 */
public class WxUtil {


    public static Map<String, String> getSessionKeyAndOpenid(String code){

        Map<String, String> result = new HashMap<>(3);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("appid", WxloginVO.Appid);
        params.add("secret", WxloginVO.AppSecret);
        params.add("js_code", code);
        params.add("grant_type", "authorization_code");

        String res = HttpUtil.sendPostRequest(UrlEnums.CODE2SESSION.getUrl(), params);

        JSONObject responseSTR = JSONObject.parseObject(res); //将字符串{“id”：1}

        Object err = responseSTR.get("errcode");

        if (err != null) {

            throw new SCException(ResultEnum.WX_LOGIN_ERROR);
        }

        String sessionKey = responseSTR.get("session_key").toString();
        String openId = responseSTR.get("openid").toString();

        Object jsonUnionId = responseSTR.get("unionid");

        if (ObjectUtil.isNotEmpty(jsonUnionId)){
            result.put("unionid", jsonUnionId.toString());
        }

        result.put("session_key",sessionKey);
        result.put("openid",openId);
        return result;
    }

    /**
     * 解密用户敏感数据获取用户信息
     * @param sessionKey 数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     */
    public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

}
