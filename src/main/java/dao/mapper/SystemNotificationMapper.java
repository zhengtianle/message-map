package dao.mapper;

import org.apache.ibatis.annotations.Select;
import pojo.SystemNotification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-3
 * @Author ZhengTianle
 * Description:
 */
public interface SystemNotificationMapper {

    /**
     * 获取从注册至今所有的系统通知
     */
    @Select("select * from system_notification where time > #{registerTime}")
    List<SystemNotification> getSysNotification(String registerTime);
}
