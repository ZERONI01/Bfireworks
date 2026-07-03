package com.zero.bfireworks.util;

import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EveryCanUse {

    private static UserService userService;
    @Resource
    public void setUserService(UserService userService) {
        EveryCanUse.userService = userService;
    }
    public static void checkAdmin(String auth){
        //TODO: 鉴权
        User me =userService.getByToken(auth.replace("Bearer ", ""));
        if (me==null || !"admin".equals(me.getRole())){
            throw new RuntimeException("需要管理员权限");
        }
    }

}
