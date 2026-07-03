package com.zero.bfireworks.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String role;

    @TableLogic
    private Integer isDeleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
}
