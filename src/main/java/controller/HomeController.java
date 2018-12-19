package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-28
 * @Author ZhengTianle
 * Description:
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)    //处理对“/”的GET请求
    public String test(){
        return "home";          //视图名为home
    }
}
