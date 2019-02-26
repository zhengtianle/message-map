package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.MessageService;
import service.StarMessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-26
 * @Author ZhengTianle
 * Description:
 */
@RestController
public class StarMessageController {

    @Autowired
    private StarMessageService starMessageService;

    @RequestMapping(value = "/starAMessage", method = RequestMethod.POST)
    public Map<String, String> starAMessage(HttpServletRequest request, HttpServletResponse response) {
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer mid = Integer.valueOf(request.getParameter("mid"));


        return starMessageService.starAMessage(uid, mid);
    }
}
