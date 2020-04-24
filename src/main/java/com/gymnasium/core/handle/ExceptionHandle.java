package com.gymnasium.core.handle;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author 王志鹏
 * @title: ExceptionHandle
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/28 9:32
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {

        if (e instanceof SCException) {
            SCException scexception = (SCException) e;
            return ResultUtil.error(scexception.getCode(), scexception.getMessage());
        } else if (e instanceof UnauthorizedException) {
            logger.error("【shiro异常】{}", e);
            return ResultUtil.error(2, "没有权限，请联系管理员授权");
        } else if (e instanceof MaxUploadSizeExceededException) {
            logger.error("文件超出10mb,无法上传", e);
            return ResultUtil.error(100478, "文件超出10mb,无法上传");
        } else if (e instanceof BindException) {
            logger.error("参数校验异常", e);
            FieldError fieldError = ((BindException) e).getFieldError();
            logger.error("变量：{} {},当前值为 {}", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
            return ResultUtil.error(1, fieldError.getDefaultMessage());
        }
        else if (e instanceof UnauthenticatedException) {
            logger.error("用户未登录");
         return ResultUtil.error(1, "用户未登录");
        } else {
            logger.error("【系统异常】{}", e);
            return ResultUtil.error(-1, "未知错误");
        }

    }
}
