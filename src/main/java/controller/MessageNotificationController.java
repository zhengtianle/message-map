package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.MessageNotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-28`
 * @Author ZhengTianle
 * Description:
 */
@RestController
public class MessageNotificationController {

    @Autowired
    private MessageNotificationService messageNotificationService;

    @RequestMapping(value = "/getMsgNotification", method = RequestMethod.POST)
    public Map<String,Object> getMsgNotification(HttpServletRequest request, HttpServletResponse response) {
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        return messageNotificationService.getMsgNotification(uid);
    }
}
