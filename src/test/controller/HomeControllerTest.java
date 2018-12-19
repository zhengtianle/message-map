package controller;

import config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-28
 * @Author ZhengTianle
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration//Web项目必须加上
@ContextConfiguration(classes = {WebConfig.class})
public class HomeControllerTest {

    @Autowired
    private HomeController homeController;

    @Test
    public void testHomePage() throws Exception {
        //HomeController homeController = new HomeController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();//搭建MockMvc
        /**
         * 对“/”执行GET请求
         * 预期得到home视图
         */
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"));

        System.out.println("预期正确！springmvc测试成功！");
    }
}
