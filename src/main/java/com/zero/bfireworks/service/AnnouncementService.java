package com.zero.bfireworks.service;

import com.zero.bfireworks.entity.Announcement;
import java.util.List;

public interface AnnouncementService {
    List<Announcement> listAll();                              // 查全部
    Announcement create(String title, String content, Boolean pinned);  // 增
    Announcement update(Integer id, String title, String content, Boolean pinned); // 改
    void delete(Integer id);                                   // 删
}
