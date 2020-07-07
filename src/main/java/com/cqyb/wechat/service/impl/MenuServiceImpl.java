package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.entity.menu.BaseButton;
import com.cqyb.wechat.entity.menu.Menu;
import com.cqyb.wechat.entity.menu.ViewButton;
import com.cqyb.wechat.service.MenuService;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/18 20:08
 * @Description:
 */
@Service
public class MenuServiceImpl  implements MenuService, ApplicationRunner {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WeChatConfig config;
    @Override
    public Menu initMenu() {
        ViewButton btn11 = new ViewButton();
        btn11.setName("获取用户列表");
        btn11.setType("view");
        btn11.setUrl("https://www.aliyun.com/");

        ViewButton btn21 = new ViewButton();
        btn21.setName("阿里云");
        btn21.setType("view");
        btn21.setUrl("https://www.aliyun.com/");

        ViewButton btn31 = new ViewButton();
        btn31.setName("个人中心");
        btn31.setType("view");
        btn31.setUrl("https://www.aliyun.com/");

        ViewButton btn41 = new ViewButton();
        btn41.setName("关注");
        btn41.setType("view");
        btn41.setUrl("https://www.aliyun.com/");
        BaseButton mainBtn1=new BaseButton();
        mainBtn1.setName("测试");
        mainBtn1.setSub_button(new BaseButton[] {btn11, btn21});
        BaseButton mainBtn2=new BaseButton();
        mainBtn2.setName("我的");
        mainBtn2.setSub_button(new BaseButton[] {btn31, btn41});

        /**

         *在某个一级菜单下没有二级菜单的情况，menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new BaseButton[] { mainBtn1, mainBtn2});
        return menu;
    }

    @Override
    public void createMenu() {
        // 从redis中获取token
        String token=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        // 拼装创建菜单的url
       String url =config.createMenuUrl+token;
        // 将菜单对象转换成json字符串
        String menuString=JSON.toJSONString(initMenu());
        // 调用接口创建菜单
        String result=HttpClientUtil.doPostJson(url,menuString);
        JSONObject json= JSON.parseObject(result);
        String errCode=json.get("errcode").toString();
        if("0".equals(errCode)){
          System.out.println("创建菜单成功===========");
        }else{
            System.out.println("创建菜单失败===========");
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动后，执行createMenu方法");
       createMenu();
    }
}
