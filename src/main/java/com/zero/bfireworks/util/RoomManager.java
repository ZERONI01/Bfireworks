package com.zero.bfireworks.util;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class RoomManager {
    //定义房间集合
    private final ConcurrentHashMap<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    //用户加入
    public void join(String roomId, WebSocketSession session) {
        rooms.computeIfAbsent(roomId,k -> new CopyOnWriteArraySet<>()).add(session);
    }

    //用户离开
    public void leave(String roomId, WebSocketSession session) {
        Set<WebSocketSession> room = rooms.get(roomId);
        if (room != null) {
            room.remove(session);
            if (room.isEmpty()) {
                rooms.remove(roomId);//移除空房间
            }
        }
    }
    //获取房间内的用户
    public Set<WebSocketSession> getSessions(String roomId){
        return rooms.getOrDefault(roomId, Collections.emptySet());
    }
    //homeroom
    public int getRoomCount(){
        return rooms.size();
    }

}
