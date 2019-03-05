package service;

import dao.mapper.MessageMapper;
import dao.mapper.MessageNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.MessageNotification;

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
public class MessageNotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageNotificationService.class);

    @Autowired
    private MessageNotificationMapper messageNotificationMapper;

    @Autowired
    private MessageMapper messageMapper;

    public Map<String, Object> getMsgNotification(Integer uid) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            List<MessageNotification> messageNotificationList = messageNotificationMapper.getMsgNotificationByRuid(uid);
            messageNotificationList.forEach(notification -> {
                switch (notification.getTitle()) {
                    case "点赞":
                        //从数据库中查询具体评论，并填入当前notification中
                        notification.setMessage(messageMapper.getMessageByMid(Integer.valueOf(notification.getContent())));
                        break;
                    default: break;
                }
            });
            resultMap.put("result", "success");
            resultMap.put("notification", messageNotificationList);
        } catch (Exception e) {
            resultMap.put("result", "error");
            LOG.warn("获取用户消息提醒异常");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 将message_notification表中time<=readtime的read全部置为1，表示已读
     */
    @Transactional
    public void readMsgNotification(String readtime) {
        try{
            //修改表read=1
            messageNotificationMapper.markRead(readtime);
        } catch (Exception e) {
            LOG.warn("更新message_notification表中time<=" + readtime + "的read为1异常");
            e.printStackTrace();
        }
    }
}
