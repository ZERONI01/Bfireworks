package com.zero.bfireworks.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("messages")
public class Messages {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private String author;
    private String type;
    private String status;
    private LocalDateTime createdAt;
}
