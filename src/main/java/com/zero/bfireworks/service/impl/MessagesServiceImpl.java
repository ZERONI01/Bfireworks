package com.zero.bfireworks.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.zero.bfireworks.entity.Messages;
import com.zero.bfireworks.mapper.MessagesMapper;
import com.zero.bfireworks.service.MessagesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Resource
    private MessagesMapper messagesMapper;
    @Override
    public List<Messages> listByType(String type) {
        return Db.lambdaQuery(Messages.class)
                .eq(Messages::getStatus, "approved")
                .eq(Messages::getType, type)
                .orderByDesc(Messages::getCreatedAt)
                .list();
    }

    @Override
    public List<Messages> listPending() {
        return Db.lambdaQuery(Messages.class)
                .eq(Messages::getStatus, "pending")
                .orderByDesc(Messages::getCreatedAt)
                .list();
    }

    @Override
    public Messages create(String content, String author, String type) {
        //内容及长度校验
        if (content == null || content.isEmpty()) throw new RuntimeException("内容不能为空");
        if (content.length() >500){
            throw new RuntimeException("内容不能超过500字");
        }
        //类型格式校验
        if(!List.of("messages","issue","suggest").contains( type)){
            throw new RuntimeException("类型格式错误");
        }
        //名称校验
        if (author == null || author.trim().isEmpty()){
            author = "匿名";
        }
        if (author.length() > 30) {
            throw new RuntimeException("署名不能超过30字");
        }
        //防刷屏
        Messages dup = Db.lambdaQuery(Messages.class)
                .eq(Messages::getAuthor, author)
                .eq(Messages::getContent, content)
                .gt(Messages::getCreatedAt, LocalDateTime.now().minusMinutes(1))
                .one();
        if (dup != null){
            throw new RuntimeException("请勿重复提交");
        }
        Messages m = new Messages();
        m.setContent(content);
        m.setAuthor(author);
        m.setType(type);
        m.setStatus("pending");
        messagesMapper.insert(m);
        return m;
    }

    @Override
    public Messages update(Integer id, String content, String author, String type) {
        Messages m = messagesMapper.selectById(id);
        if (m == null) throw new RuntimeException("留言不存在");
        m.setContent(content);
        m.setAuthor(author);
        m.setType(type);
        messagesMapper.updateById(m);
        return m;
    }

    @Override
    public void delete(Integer id) {
        int rows = messagesMapper.deleteById(id);
        if (rows == 0) throw new RuntimeException("留言不存在");

    }

    @Override
    public void approve(Integer id) {
        Messages m = messagesMapper.selectById(id);
        if (m == null) throw new RuntimeException("留言不存在");
        m.setStatus("approved");
        messagesMapper.updateById(m);
    }
}
