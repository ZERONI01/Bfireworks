package com.zero.bfireworks.service.impl;

import com.zero.bfireworks.entity.Announcement;
import com.zero.bfireworks.mapper.AnnouncementMapper;
import com.zero.bfireworks.service.AnnouncementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> listAll() {
        return announcementMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Announcement>()
                .orderByDesc(Announcement::getPinned)
                .orderByDesc(Announcement::getCreatedAt)
        );
    }

    @Override
    public Announcement create(String title, String content, Boolean pinned) {
        //标题校验
        if (title == null || title.isEmpty()) throw new RuntimeException("标题不能为空");
        //内容校验
        if (content == null || content.isEmpty()) throw new RuntimeException("内容不能为空");
        Announcement a = new Announcement();
        a.setTitle(title);
        a.setContent(content);
        a.setPinned(pinned != null && pinned ? 1 : 0);
        announcementMapper.insert(a);
        return a;
    }

    @Override
    public Announcement update(Integer id, String title, String content, Boolean pinned) {
        Announcement a = announcementMapper.selectById(id);
        if (a == null) throw new RuntimeException("公告不存在");
        a.setTitle(title);
        a.setContent(content);
        a.setPinned(pinned != null && pinned ? 1 : 0);
        announcementMapper.updateById(a);
        return a;
    }

    @Override
    public void delete(Integer id) {
        int rows = announcementMapper.deleteById(id);
        if (rows == 0) throw new RuntimeException("公告不存在");
    }
}
