package com.zero.bfireworks.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.zero.bfireworks.entity.Jump;
import com.zero.bfireworks.mapper.JumpMapper;
import com.zero.bfireworks.service.JumpService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JumpServiceImpl implements JumpService {
    @Resource
    private JumpMapper JumpMapper;
    @Override
    public Jump create(String name, Integer score, Integer id) {
        Jump jump = new Jump();
        jump.setId(Integer.valueOf(id));
        jump.setName(name);
        jump.setScore(score);
        jump.setCreatedAt(LocalDateTime.now());
        JumpMapper.insert(jump);
        return jump;
    }

    @Override
        public Jump update(Integer id, String name, Integer score) {
        Jump jump = JumpMapper.selectById(id);
        jump.setName(name);
        jump.setScore(score);
        jump.setCreatedAt(LocalDateTime.now());
        JumpMapper.updateById(jump);
        return jump;
    }

    @Override
    public List<Jump> list() {
        return Db.lambdaQuery(Jump.class)
                .orderByDesc(Jump::getScore)
                .orderByDesc(Jump::getCreatedAt)
                .list();
    }

    @Override
    public void delete(Integer id) {
        int rows = JumpMapper.deleteById(id);
        if(rows == 0){
            throw new RuntimeException("记录不存在");
        }
    }

    @Override
    public Jump getById(Integer id) {
        Jump jump =Db.lambdaQuery(Jump.class)
                .eq(Jump::getId, id)
                .orderByDesc(Jump::getScore)
                .getEntity();
        if (jump ==null){
            throw new RuntimeException("记录不存在");
        }
        return jump;
    }
}
