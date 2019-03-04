package service;

import dao.mapper.SystemNotificationMapper;
import dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.SystemNotification;
import pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-3
 * @Author ZhengTianle
 * Description:
 */
@Service
public class SystemNotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(SystemNotificationService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemNotificationMapper systemNotificationMapper;

    public Map<String, Object> getSysNotification(Integer uid) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            User currentUser = userMapper.getUserByUid(uid);
            //获取开始注册至现今所有的系统通知
            List<SystemNotification> systemNotificationList =
                    systemNotificationMapper.getSysNotification(currentUser.getTime());

            if(currentUser.getReadtime().equals(currentUser.getTime())) {
                //注册时间和最近一次阅读时间一样，说明是刚刚注册用户(未阅读过系统通知)
                //增加新用户通知
                SystemNotification newUserNotification1 =
                        new SystemNotification("恭喜您成功注册留言山威",
                                "“留言山威”是一款主打可视化留言的系统，支持点击标注留言，世界留言，轻度导航定位等日常功能，个人中心的消息通知等。如有疑问请移步github: <a href=\"https://github.com/zhengtianle/message-map\">留言山威Github</a>",
                                currentUser.getTime(),
                                false);
                systemNotificationList.add(newUserNotification1);
            }

            systemNotificationList.forEach(notification -> {
                if(currentUser.getReadtime().compareTo(notification.getTime()) < 0) {
                    //通知的时间在用户阅读时间之后的，标明未读
                    notification.setHasRead(false);
                }
            });

            resultMap.put("result", "success");
            resultMap.put("notification", systemNotificationList);
        }catch (Exception e) {
            LOG.warn("获取系统消息异常");
            e.printStackTrace();
            resultMap.put("result", "error");
        }
        return resultMap;
    }

    /**
     * 将用户表中的readtime置为传参#{readtime}
     */
    public void readSysNotification(String readtime) {
        try{
            User user = new User();
            user.setReadtime(readtime);
            userMapper.updateSelective(user);
        } catch (Exception e) {
            LOG.warn("更新user表中的readtime为 " + readtime +" 异常");
            e.printStackTrace();
        }
    }
}
