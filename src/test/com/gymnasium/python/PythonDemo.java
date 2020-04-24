package com.gymnasium.python;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 边书恒
 * @Title: PythonDemo
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/23 16:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PythonDemo {

    @Test
    public void pythonTest(){
        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
    }
}
