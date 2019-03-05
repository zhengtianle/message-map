package service;

import dao.mapper.MessageMapper;
import dao.mapper.MessageNotificationMapper;
import dao.mapper.StarMessageMapper;
import dao.provider.StarMessageSqlProvider;
import notice.StarNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Message;
import pojo.MessageNotification;
import pojo.StarMessage;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-26
 * @Author ZhengTianle
 * Description:
 */
@Service
public class StarMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(StarMessageService.class);

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StarMessageMapper starMessageMapper;

    @Autowired
    private MessageNotificationMapper messageNotificationMapper;

    @Transactional
    public Map<String, String> starAMessage(int uid, int mid) {
        Map<String, String> resultMap = new HashMap<>();
        StarMessage starMessage = new StarMessage();
        MessageNotification messageNotification = new MessageNotification();
        try {
            //mid留言点赞数+1
            Message message = new Message();
            message.setMid(mid);
            message.setStars(1);//设置任何值都只会使得数据库中stars+1，详见updateByPrimaryKeySelective
            messageMapper.updateByPrimaryKeySelective(message);

            //获取留言者id
            int toUid = messageMapper.getUidByMid(mid);

            //插入starMessage记录
            starMessage.setMid(mid);
            starMessage.setSfid(uid);
            starMessage.setStid(toUid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());
            starMessage.setTime(time);
            int affectedRows = starMessageMapper.insert(starMessage);
            if(affectedRows == 1){
                resultMap.put("result", "success");
            } else {
                resultMap.put("result", "error");
            }
            //通知被点赞的用户
            if(toUid != uid) {
                messageNotification.setSuid(uid);
                messageNotification.setRuid(toUid);
                messageNotification.setTitle("点赞");
                messageNotification.setContent(String.valueOf(mid));
                messageNotification.setTime(sdf.format(new Date()));
                messageNotificationMapper.insert(messageNotification);
            }
        } catch (Exception e) {
            LOG.warn("企图更新表message中mid为 " + mid + " 的star数+1");
            LOG.warn("企图向表star_message插入：" + starMessage.toString());
            LOG.warn("企图向表message_notification插入：" + messageNotification.toString());
            e.printStackTrace();
            resultMap.put("result", "error");
        }

        return resultMap;
    }

    /**
     * @param uid 当前登录用户id
     * @return 此用户点过赞的评论id集合
     */
    public List<Integer> getStaredMessageBySfid(Integer uid) {
        StarMessage starMessage = new StarMessage();
        starMessage.setSfid(uid);
        List<StarMessage> midList = starMessageMapper.getStaredSelective(starMessage);
        List<Integer> list = new ArrayList<>();
        midList.forEach(sm -> list.add(sm.getMid()));
        return list;
    }
}
