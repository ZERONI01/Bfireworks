package com.zero.bfireworks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("jumpifucan")
public class Jump {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer score;
    @TableLogic
    private Integer isDeleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
}
