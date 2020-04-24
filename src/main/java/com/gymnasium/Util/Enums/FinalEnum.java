package com.gymnasium.Util.Enums;

/**
 * @author 王志鹏
 * @title: FinalEnum
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/30 10:04
 */
public enum FinalEnum {

    URL_HEAD("http://39.106.78.98/", "fdfs链接请求头"),
    EXPRESS_KEY("nJbGglMm8238","物流身份授权Key"),
    EXPRESS_CUSTOMER("8BAE319E074471FC428FA289EA684096","物流分配的公司编号")

    ;

    private String content;

    private String rmk;

    FinalEnum(String content, String rmk) {
        this.content = content;
        this.rmk = rmk;
    }

    public String getContent() {
        return content;
    }

    public String getRmk() {
        return rmk;
    }
}
