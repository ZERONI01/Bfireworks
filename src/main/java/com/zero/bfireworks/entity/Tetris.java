package com.zero.bfireworks.entity;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Tetris")
public class Tetris{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer score;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
}