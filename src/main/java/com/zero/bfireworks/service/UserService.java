package com.zero.bfireworks.service;

import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.vo.UserVO;
import java.util.List;

public interface UserService {
    User login(UserVO.LoginVO vo);
    User register(UserVO.RegisterVO vo);
    User getByToken(String token);
    void logout(String token);
    List<User> listAll();
    User create(UserVO.UserSaveVO vo);
    User update(Integer id, UserVO.UserSaveVO vo);
    void delete(Integer id);
    void changePassword(Integer userId, UserVO.ChangePasswordVO vo);
}
