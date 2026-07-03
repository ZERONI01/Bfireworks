package com.zero.bfireworks.service;

import com.zero.bfireworks.entity.Announcement;
import com.zero.bfireworks.entity.Messages;
import com.zero.bfireworks.vo.MessagesVO;

import java.util.List;

public interface MessagesService {
    List<Messages> listByType(String type);                         // 按类型查已审核
    List<Messages> listPending();                                   // 查所有待审核
    Messages create(String content, String author, String type);  // 增
    Messages update(Integer id, String content, String author, String type); // 改
    void delete(Integer id);
    void approve(Integer id);
}
