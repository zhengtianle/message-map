package websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-15
 * @Author ZhengTianle
 * Description:
 * WebSocket配置处理器
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Bean
    public MyWebSocketHandler getHandler() {
        return new MyWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //普通浏览器访问所用，使用大多数浏览器支持websocket
        webSocketHandlerRegistry.addHandler(getHandler(), "/webSocketServer").addInterceptors(new MyWebSocketInterceptor());
        //适应IE低版本浏览器所用的，因为IE11以下的浏览器都不支持websocket，所以需要前台jsp页面用这个网址访问注册对应的注册器。
        webSocketHandlerRegistry.addHandler(getHandler(), "/sockjs/webSocketServer").addInterceptors(new MyWebSocketInterceptor()).withSockJS();
    }
}
