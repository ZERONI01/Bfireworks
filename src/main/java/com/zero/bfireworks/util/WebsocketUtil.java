package com.zero.bfireworks.util;

import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebsocketUtil implements HandshakeInterceptor {
    @Resource
    private UserService userService;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            String token = httpServletRequest.getParameter("token");

            if (token ==null || token.isEmpty()){
                return false;
            }
            User user = userService.getByToken(token);
            if (user ==null || user.getId() == null ){
                return false;
            }
            attributes.put("currentUser",user);

            String roomId = httpServletRequest.getParameter("roomId");
            attributes.put("roomId",roomId);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

    }
}
