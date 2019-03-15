package websocket;

import config.RootConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-15
 * @Author ZhengTianle
 * Description:
 * 拦截用户登录信息，并将用户登录信息交给websocket的WebSocketSession来管理
 */
public class MyWebSocketInterceptor implements HandshakeInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MyWebSocketInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        LOG.info("WebSocket: 用户[ID: " +
                ((ServletServerHttpRequest) request).getServletRequest()
                        .getParameter("uid") + "]已经建立连接");
        if(request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            // 标记用户
            Integer uid = Integer.valueOf(httpRequest.getParameter("uid"));
            if(uid != null) {
                map.put("uid", uid);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
        LOG.info("afterHandshake!!!");
    }
}
