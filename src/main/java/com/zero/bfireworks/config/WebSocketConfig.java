package com.zero.bfireworks.config;

import com.zero.bfireworks.Handler.FireworkWebSocketHandler;
import com.zero.bfireworks.util.WebsocketUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private FireworkWebSocketHandler fireworkWebSocketHandler;

    @Resource
    private WebsocketUtil authInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(fireworkWebSocketHandler, "/ws/firework")
                .addInterceptors(authInterceptor)
                .setAllowedOrigins("*");

    }
}
