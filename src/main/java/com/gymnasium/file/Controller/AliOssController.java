package com.gymnasium.file.Controller;


import com.gymnasium.Util.oss.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags ="上传图片")
@RequestMapping(value = "/system")
public class AliOssController {


    /**
     * 上传图片
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "上传图片")
    @RequestMapping(value = "/oss/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Map fileUpload(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "上传成功！");
        result.put("data", FileUtils.upload(request));
        return result;
    }

    /**
     * 删除图片
     *
     * @return
     */
    @RequestMapping(value = "/oss/deleteFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteFileUpload(@RequestParam(value = "image", required = false) String image) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        FileUtils.deleteFile(image);
        result.put("msg", "删除成功！");

        result.put("data", "");
        return result;
    }

}
