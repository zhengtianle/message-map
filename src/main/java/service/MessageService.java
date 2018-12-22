package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import dao.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Message;
import pojo.UserAndMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-22
 * @Author ZhengTianle
 * Description:
 */
@Service
public class MessageService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageMapper messageMapper;

    public String leaveAMessage(Message message){
        int affectedRows = 0;
        Gson gson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        try{
            affectedRows = messageMapper.insertSelective(message);
            if(affectedRows == 1){
                resultMap.put("result", "success");
            } else {
                resultMap.put("result", "error");
            }
        } catch (Exception e){
            resultMap.put("result", "error");
        }
        LOG.info("新插入留言信息的返回json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }

    public String getMessages(int page, String location){
        Gson gson = new Gson();
        Map<String,Object> resultMap = new HashMap<>();
        Message message = new Message();
        message.setLocation(location);
        try{
            //一次查八条
            PageHelper.startPage(page,8);
            List<UserAndMessage> resultList = messageMapper.selectSelective(message);
            PageInfo pageInfo = new PageInfo(resultList);
            resultMap.put("result", "success");
            resultMap.put("pages", pageInfo.getPages());
            resultMap.put("content",gson.toJson(resultList));

        } catch (Exception e){
            resultMap.put("result", "error");
        }
        LOG.info("分页查询的留言信息json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }
}
