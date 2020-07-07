package com.cqyb.wechat;

import com.cqyb.wechat.service.MenuService;
import com.cqyb.wechat.service.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTests {
    @Autowired
    private MenuService menuService;
    @Autowired
    private TemplateService templateService;

    @Test
    public void contextLoads() {
        Map<String, String> map=new HashMap<>();
        map.put("userName","颜彪");
        map.put("music","雅俗共赏");
        map.put("url","http://fanyi.youdao.com/");
        templateService.sendMusicTemplate(map);
    }

}
