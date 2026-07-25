package com.zero.bfireworks.service;
import com.zero.bfireworks.entity.Tetris;
import java.util.List;

public interface TetrisService {
    Tetris create(String name,Integer score,Integer id);
    Tetris update(String name,Integer score,Integer id);
    List<Tetris> list();
    Tetris getById(Integer id);
    void delete(Integer id);
}
