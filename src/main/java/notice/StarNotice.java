package notice;

import dao.mapper.MessageMapper;
import dao.mapper.MessageNotificationMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.MessageNotification;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-27
 * @Author ZhengTianle
 * Description:
 * 利用spring AOP
 * 当用户点赞后，通知被点赞的用户
 */
@Aspect
public class StarNotice {
    private static final Logger LOG = LoggerFactory.getLogger(StarNotice.class);

    @Autowired
    private MessageNotificationMapper messageNotificationMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Pointcut("execution(* service.StarMessageService.starAMessage(int, int)) && args(uid ,mid)")
    public void star(int uid, int mid){}

    @AfterReturning("star(uid, mid)")
    public void notice(int uid, int mid){
        MessageNotification messageNotification = new MessageNotification();
        try{
            int ruid = messageMapper.getUidByMid(mid);//得到ruid
            //自己给自己点赞时，不用通知，也就不用插入消息通知表
            if(ruid != uid) {
                messageNotification.setSuid(uid);
                messageNotification.setRuid(ruid);
                messageNotification.setTitle("点赞");
                messageNotification.setContent(String.valueOf(mid));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                messageNotification.setTime(sdf.format(new Date()));
                messageNotificationMapper.insert(messageNotification);
            }
        } catch (Exception e) {
            LOG.warn("点赞通知插入异常，应插入的数据为：" + messageNotification.toString());
            e.printStackTrace();
        }

    }
}
