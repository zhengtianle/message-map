package controller;

import dao.provider.UserSqlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.User;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-18
 * @Author ZhengTianle
 * Description:
 */
@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

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

    @RequestMapping(value = "/updateBasicInfo", method = RequestMethod.POST)
    public void updateBasicInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String username = request.getParameter("username");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String address = request.getParameter("address");
        String profile = request.getParameter("profile");
        User user = new User();
        user.setUid(uid);
        user.setUsername(username);
        if(sex.equals("男")){
            user.setSex(1);
        } else {
            user.setSex(0);
        }
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setProfile(profile);
        LOG.info("updateBasicInfo中传参user：" + user.toString());
        String result = userService.updateBasicInfo(user);
        out.print(result);
    }

    @RequestMapping(value = "/updateMySduwh", method = RequestMethod.POST)
    public void updateMySduwh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String sid = request.getParameter("sid");
        String institute = request.getParameter("institute");
        User user = new User();
        user.setUid(uid);
        user.setSid(sid);
        user.setInstitute(institute);

        //String result = userService.register(mobile, pin, psd);
        //out.print(result);
    }

}
