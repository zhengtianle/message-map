package dao.mapper;

import org.apache.ibatis.annotations.Select;
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
    @Select("select * from message_notification where ruid = #{uid}")
    List<MessageNotification> getMsgNotificationByRuid(int uid);
}
