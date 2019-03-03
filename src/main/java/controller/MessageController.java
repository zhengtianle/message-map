package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Message;
import service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-22
 * @Author ZhengTianle
 * Description:
 */
@Controller
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getMessages", method = RequestMethod.POST)
    public void getMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Integer page = Integer.valueOf(request.getParameter("page"));
        String location = request.getParameter("location");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Message message = new Message();
        message.setLocation(location);
        //一次查八条
        String result = messageService.getMessages(page,8,message,uid);

        out.print(result);
    }

    @RequestMapping(value = "/leaveAMessage", method = RequestMethod.POST)
    public void leaveAMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String location = request.getParameter("location");
        String content = request.getParameter("content");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Message message = new Message();
        message.setUid(uid);
        message.setLocation(location);
        message.setContent(content);
        message.setTime(time);
        LOG.info("即将插入的留言信息：" + message.toString());

        String result = messageService.leaveAMessage(message);
        out.print(result);
    }

    @RequestMapping(value = "/myMessages", method = RequestMethod.POST)
    public void myMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Message message = new Message();
        message.setUid(uid);
        LOG.debug(message.toString());

        String result = messageService.myMessages(page,limit, message);

        out.print(result);
    }
}
