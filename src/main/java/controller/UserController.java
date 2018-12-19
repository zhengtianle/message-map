package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-18
 * @Author ZhengTianle
 * Description:
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 接收手机号和密码
     * 验证是否可以登录
     * print json字符串
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void loginCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String mobile = request.getParameter("mobile");
        String psd = request.getParameter("psd");
        String result = userService.login(mobile, psd);

        out.print(result);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String mobile = request.getParameter("mobile");
        String pin = request.getParameter("pin");
        String psd = request.getParameter("psd");
        String result = userService.register(mobile, pin, psd);

        out.print(result);
    }

}
