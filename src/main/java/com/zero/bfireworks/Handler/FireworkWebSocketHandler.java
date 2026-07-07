package com.zero.bfireworks.Handler;




import com.zero.bfireworks.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArraySet;


@Component
public class FireworkWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(FireworkWebSocketHandler.class);
    //线程安全的Set，存放所有在线烟花的观众
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    //建立连接后触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        sessions.add(session);
        User user =(User) session.getAttributes().get("currentUser");
        log.info("用户{}进入烟花模块，当前在线人数:{}", user.getUsername(),sessions.size());
    }
    //收到消息后触发
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        //遍历在线连接，发送坐标
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()){
                //异步发送
                webSocketSession.sendMessage(message);
            }
        }
    }
    //断开触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
        User user =(User) session.getAttributes().get("currentUser");
        log.info("用户{}离开，当前在线:{}",user != null ? user.getUsername() : "未知用户",sessions.size());
    }


}
