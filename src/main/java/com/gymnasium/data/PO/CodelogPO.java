package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CodelogPO
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/22 17:38
 */

@Data
@Entity
@Table(name = "codelog", catalog = "gymnasium")
@ApiModel(value = "Codelog对象", description = "code日志")
public class CodelogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "验证码日志表主键", name = "codeId;", example = "1")
    @Column(name = "codeId")
    private Integer codeId;

    @Column(name = "codePhone")
    @ApiModelProperty(value = "手机号", name = "codePhone;", example = "186265846589")
    private String codePhone;

    @Column(name = "code")
    @ApiModelProperty(value = "验证码", name = "code;", example = "123465")
    private String code;

    @Column(name = "type")
    @ApiModelProperty(value = "使用验证码功能类型", name = "type;", example = "0:初始,1:注册,2登录,3.修改密码")
    private Short type;

    @Column(name = "errlog")
    @ApiModelProperty(value = "短信接口错误日志", name = "errlog;", example = "")
    private String errlog;

    @Column(name = "creatTime")
    @ApiModelProperty(value = "创建时间", name = "creatTime;", example = "")
    private Timestamp creatTime;

}
