package dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.MessageNotification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-3
 * @Author ZhengTianle
 * Description:
 */
public interface MessageNotificationMapper {

    /**
     * 获取当前用户收到的所有的消息提醒（不包括系统通知）,比如点赞
     */
    @Select({
            "select username susername, title, content, message_notification.time, `read` from message_notification, user ",
            "where user.uid = message_notification.suid and ruid = #{uid} order by time desc"
    })
    List<MessageNotification> getMsgNotificationByRuid(int uid);

    @Insert({
            "insert into message_notification(suid, ruid, title, content, time) ",
            "values(#{suid}, #{ruid}, #{title}, #{content}, #{time})"
    })
    int insert(MessageNotification messageNotification);

    @Update("update message_notification set `read` = 1 where time <= #{readtime}")
    void markRead(String readtime);
}
