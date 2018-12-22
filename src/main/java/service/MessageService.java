package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public String getMessages(int page, int limit, Message message){
        Gson gson = new Gson();
        Map<String,Object> resultMap = new HashMap<>();
        try{
            PageHelper.startPage(page,limit);
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

    public String myMessages(int page, int limit, Message message){
        Gson gson = new Gson();
        Map<String,Object> resultMap = new HashMap<>();
        try{
            PageHelper.startPage(page,limit);
            List<UserAndMessage> resultList = messageMapper.selectSelective(message);
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray jsonArray = parser.parse(gson.toJson(resultList)).getAsJsonArray();

            PageInfo pageInfo = new PageInfo(resultList);
            resultMap.put("code", 0);
            resultMap.put("count", pageInfo.getTotal());
            resultMap.put("msg","");
            resultMap.put("data",jsonArray);

        } catch (Exception e){
            resultMap.put("code", 1);
        }
        LOG.info("分页查询的留言信息json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }
}
