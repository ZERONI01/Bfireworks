package com.zero.bfireworks.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.zero.bfireworks.entity.Tetris;
import com.zero.bfireworks.mapper.TetrisMapper;
import com.zero.bfireworks.service.TetrisService;

import jakarta.annotation.Resource;

@Service
public class TetrisServiceImpl implements TetrisService{
    @Resource 
    private TetrisMapper tetrisMapper;
    @Override
    public Tetris create(String name, Integer score, Integer id) {
        Tetris tetris = new Tetris();
        tetris.setId(Integer.valueOf(id));
        tetris.setName(name);
        tetris.setScore(score);
        tetris.setCreatedAt(LocalDateTime.now());
        tetrisMapper.insert(tetris);
        return tetris;
    }
    @Override
    public Tetris update(String name, Integer score, Integer id) {
        Tetris tetris = tetrisMapper.selectById(id);
        tetris.setName(name);
        tetris.setScore(score);
        tetris.setCreatedAt(LocalDateTime.now());
        tetrisMapper.updateById(tetris);
        return tetris;
    }
    @Override
    public List<Tetris> list() {
        return Db.lambdaQuery(Tetris.class)
                .orderByDesc(Tetris::getScore)
                .orderByDesc(Tetris::getCreatedAt)
                .list();
    }
    @Override
    public Tetris getById(Integer id) {
        Tetris tetris =Db.lambdaQuery(Tetris.class)
                .eq(Tetris::getId, id)
                .orderByDesc(Tetris::getScore)
                .getEntity();
        if (tetris ==null){
            throw new RuntimeException("记录不存在");
        }
        return tetris;
    }
    @Override
    public void delete(Integer id) {
        int rows = tetrisMapper.deleteById(id);
        if(rows == 0){
            throw new RuntimeException("记录不存在");
        }
    }

}
