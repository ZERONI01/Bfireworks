package com.zero.bfireworks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.bfireworks.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
