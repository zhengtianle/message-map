package service;

import dao.mapper.MessageMapper;
import dao.mapper.StarMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Message;
import pojo.StarMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-26
 * @Author ZhengTianle
 * Description:
 */
@Service
public class StarMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StarMessageMapper starMessageMapper;

    public Map<String, String> starAMessage(int uid, int mid) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            //mid留言点赞数+1
            Message message = new Message();
            message.setMid(mid);
            message.setStars(1);//设置任何值都只会使得数据库中stars+1，详见updateByPrimaryKeySelective
            messageMapper.updateByPrimaryKeySelective(message);

            //获取留言者id
            int toUid = messageMapper.getUidByMid(mid);

            //插入starMessage记录
            StarMessage starMessage = new StarMessage();
            starMessage.setMid(mid);
            starMessage.setSfid(uid);
            starMessage.setStid(toUid);
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());
            starMessage.setTime(time);
            int affectedRows = starMessageMapper.insert(starMessage);
            if(affectedRows == 1){
                resultMap.put("result", "success");
            } else {
                resultMap.put("result", "error");
            }
            //TODO: 通知被点赞的用户，可以使用AOP
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "error");
        }

        return resultMap;
    }
}
