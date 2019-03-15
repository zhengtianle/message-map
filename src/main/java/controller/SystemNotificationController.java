package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import pojo.User;
import service.SystemNotificationService;
import websocket.MyWebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-28
 * @Author ZhengTianlej
 * Description:
 */
@RestController
public class SystemNotificationController {

    @Autowired
    private SystemNotificationService systemNotificationService;

    @Bean
    public MyWebSocketHandler getHandler() {
        return new MyWebSocketHandler();
    }

    @RequestMapping(value = "/getSysNotification", method = RequestMethod.POST)
    public Map<String, Object> getSysNotification(HttpServletResponse response, HttpServletRequest request) {
        //TODO:系统通知
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        return systemNotificationService.getSysNotification(uid);
    }

    @RequestMapping(value = "/readSysNotification", method = RequestMethod.POST)
    public void readSysNotification(HttpServletRequest request, HttpServletResponse response) {
        String readtime = request.getParameter("readtime");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        User user = new User();
        user.setReadtime(readtime);
        user.setUid(uid);
        systemNotificationService.readSysNotification(user);
    }

    /**
     * 发送系统通知提醒
     */
    @RequestMapping(value = "/sendSystemMessage", method = RequestMethod.GET)
    public void sendSystemMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            getHandler().broadcast(new TextMessage("新的系统通知"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
