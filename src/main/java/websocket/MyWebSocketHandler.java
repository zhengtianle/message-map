package websocket;

import com.google.gson.Gson;
import dao.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import pojo.Star;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-15
 * @Author ZhengTianle
 * Description:
 * websocket消息处理类
 * 主要用来做消息的逻辑处理
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyWebSocketHandler.class);

    @Autowired
    private MessageMapper messageMapper;

    //用于保存HttpSession与WebSocketSession的映射关系 uid->websocket
    public static final Map<Integer, WebSocketSession> userSocketSessionMap = new ConcurrentHashMap<>();

    /**
     * 建立连接后,把登录用户的id写入WebSocketSession
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer uid = (Integer) session.getAttributes().get("uid");
        userSocketSessionMap.putIfAbsent(uid, session);
    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(message.getPayloadLength() == 0)
            return;
        Star msg = new Gson().fromJson(message.getPayload().toString(),Star.class);

        //获取留言者id
        Integer toUid = messageMapper.getUidByMid(msg.getMid());
        //这里只是消息提醒，消息的显示由ajax查询,所以这里传递的消息不重要
        sendMessageToUser(toUid, new TextMessage("新的消息"));
    }

    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        // 移除当前抛出异常用户的Socket会话
        Iterator<Map.Entry<Integer, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer, WebSocketSession> entry = iterator.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                LOG.info("WebSocket会话因为异常已经移除: 用户ID" + entry.getKey());
            }
        }
    }

    /**
     * 连接关闭后处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOG.info("Websocket:" + session.getId() + "已经关闭");
        Iterator<Map.Entry<Integer, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        // 移除当前用户的Socket会话
        while(iterator.hasNext()) {
            Map.Entry<Integer, WebSocketSession> entry = iterator.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                LOG.info("WebSocket会话已经移除: 用户ID" + entry.getKey());
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送系统通知
     */
    public void broadcast(final TextMessage message) throws IOException {
        LOG.info(message.toString());
        Iterator<Map.Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
        //多线程群发
        while (it.hasNext()) {
            Map.Entry<Integer, WebSocketSession> entry = it.next();
            if (entry.getValue().isOpen()) {
                new Thread(() -> {
                    try {
                        if (entry.getValue().isOpen()) {
                            entry.getValue().sendMessage(message);
                        }
                    } catch (IOException e) {
                        LOG.error(e.toString());
                        e.printStackTrace();
                    }

                }).start();
            }
        }
    }

    /**
     * 给某个用户发送消息提醒
     */
    public void sendMessageToUser(Integer uid, TextMessage message) throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
}
