package com.zero.bfireworks.service;

import com.zero.bfireworks.entity.Jump;

import java.util.List;

public interface JumpService {
    Jump create(String name, Integer score, Integer id);
    Jump update(Integer id, String name, Integer score);
    List<Jump> list();
    void delete(Integer id);
    Jump getById(Integer id);
}
