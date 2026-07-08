package com.zero.bfireworks.Handler;




import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.util.RoomManager;
import jakarta.annotation.Resource;
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
    @Resource
    private RoomManager roomManager;
    //连接后触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId == null || roomId.trim().isEmpty()) {
            roomId="public";
        }
        roomManager.join(roomId, session);
        User user = (User) session.getAttributes().get("currentUser");
        log.info("用户{}加入房间{},当前人数:{}", user.getUsername(), roomId, roomManager.getSessions(roomId).size());
    }
    //收到消息后触发
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId == null || roomId.trim().isEmpty()) {
            roomId="public";
        }
        for(WebSocketSession s : roomManager.getSessions(roomId)){
            if(s.isOpen()){
                s.sendMessage(message);
            }
        }
    }
    //断开连接后触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId == null || roomId.trim().isEmpty()) {
            roomId="public";
        }
        roomManager.leave(roomId, session);
        User user = (User) session.getAttributes().get("currentUser");
        log.info("用户{}离开房间{},当前人数:{}", user.getUsername(), roomId, roomManager.getSessions(roomId).size());
    }


}
