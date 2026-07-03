package com.zero.bfireworks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.mapper.UserMapper;
import com.zero.bfireworks.service.UserService;
import com.zero.bfireworks.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    // 内存存 token，重启丢失（简单够用）
    private final Map<String, User> tokenStore = new HashMap<>();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ==================== 鉴权 ====================

    @Override
    public User login(UserVO.LoginVO vo) {
        User user = userMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, vo.getUsername())
        );
        if (user == null || !encoder.matches(vo.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return user;
    }

    @Override
    public User register(UserVO.RegisterVO vo) {
        // 校验
        if (vo.getUsername() == null || vo.getUsername().length() < 3 || vo.getUsername().length() > 20) {
            throw new RuntimeException("用户名需3-20位");
        }
        if (!vo.getUsername().matches("^[a-zA-Z0-9_\\-]+$")) {
            throw new RuntimeException("用户名只能包含字母、数字、下划线、连字符");
        }
        if (vo.getPassword() == null || vo.getPassword().length() < 6 || vo.getPassword().length() > 50) {
            throw new RuntimeException("密码需6-50位");
        }
        // 查重
        User exist = userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, vo.getUsername())
        );
        if (exist != null) throw new RuntimeException("用户名已存在");
        // 创建
        User user = new User();
        user.setUsername(vo.getUsername());
        user.setPassword(encoder.encode(vo.getPassword()));
        user.setRole("user");
        userMapper.insert(user);
        return user;
    }

    public String createToken(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, user);
        return token;
    }

    @Override
    public User getByToken(String token) {
        return tokenStore.get(token);
    }

    @Override
    public void logout(String token) {
        tokenStore.remove(token);
    }

    // ==================== 用户 CRUD（管理员） ====================

    @Override
    public List<User> listAll() {
        return userMapper.selectList(null);
    }

    @Override
    public User create(UserVO.UserSaveVO vo) {
        if (vo.getUsername() == null || vo.getUsername().length() < 3) {
            throw new RuntimeException("用户名需3-20位");
        }
        if (!vo.getUsername().matches("^[a-zA-Z0-9_\\-]+$")) {
            throw new RuntimeException("用户名只能包含字母、数字、下划线、连字符");
        }
        if (vo.getPassword() == null || vo.getPassword().length() < 6) {
            throw new RuntimeException("密码需6-50位");
        }
        User exist = userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, vo.getUsername())
        );
        if (exist != null) throw new RuntimeException("用户名已存在");

        User user = new User();
        user.setUsername(vo.getUsername());
        user.setPassword(encoder.encode(vo.getPassword()));
        user.setRole(vo.getRole() != null ? vo.getRole() : "user");
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(Integer id, UserVO.UserSaveVO vo) {
        User user = userMapper.selectById(id);
        if (user == null) throw new RuntimeException("用户不存在");
        if ("admin".equals(user.getUsername())) {
            if (vo.getUsername() != null && !"admin".equals(vo.getUsername()))
                throw new RuntimeException("admin 用户名不可修改");
            if (vo.getRole() != null && !"admin".equals(vo.getRole()))
                throw new RuntimeException("admin 角色不可修改");
        }
        if (vo.getUsername() != null) user.setUsername(vo.getUsername());
        if (vo.getPassword() != null && vo.getPassword().length() >= 6)
            user.setPassword(encoder.encode(vo.getPassword()));
        if (vo.getRole() != null) user.setRole(vo.getRole());
        userMapper.updateById(user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) throw new RuntimeException("用户不存在");
        if ("admin".equals(user.getUsername())) throw new RuntimeException("不能删除 admin");
        userMapper.deleteById(id); // @TableLogic → 软删除
    }

    @Override
    public void changePassword(Integer userId, UserVO.ChangePasswordVO vo) {
        User user = userMapper.selectById(userId);
        if (!encoder.matches(vo.getOldPassword(), user.getPassword()))
            throw new RuntimeException("旧密码错误");
        if (vo.getNewPassword() == null || vo.getNewPassword().length() < 6)
            throw new RuntimeException("新密码需6-50位");
        user.setPassword(encoder.encode(vo.getNewPassword()));
        userMapper.updateById(user);
    }
}
