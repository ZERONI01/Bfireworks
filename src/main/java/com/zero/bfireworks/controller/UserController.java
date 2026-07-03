package com.zero.bfireworks.controller;

import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.service.impl.UserServiceImpl;
import com.zero.bfireworks.util.R;
import com.zero.bfireworks.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.zero.bfireworks.util.EveryCanUse.checkAdmin;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    // ==================== 鉴权 ====================

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserVO.LoginVO vo) {
        User user = userService.login(vo);
        String token = userService.createToken(user);
        return ResponseEntity.ok(R.ok(
            "token", token,
            "user", Map.of("id", user.getId(), "username", user.getUsername(), "role", user.getRole())
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserVO.RegisterVO vo) {
        User user = userService.register(vo);
        return ResponseEntity.ok(R.ok("id", user.getId()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        userService.logout(auth.replace("Bearer ", ""));
        return ResponseEntity.ok(R.ok());
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        User user = userService.getByToken(auth.replace("Bearer ", ""));
        if (user == null) throw new RuntimeException("未登录");
        return ResponseEntity.ok(R.ok("user",
            Map.of("id", user.getId(), "username", user.getUsername(), "role", user.getRole())
        ));
    }

    // ==================== 用户 CRUD（仅管理员） ====================

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> users(@RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        checkAdmin(auth);
        List<Map<String, Object>> list = new ArrayList<>();
        for (User u : userService.listAll()) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", u.getId());
            m.put("username", u.getUsername());
            m.put("role", u.getRole());
            m.put("created_at", u.getCreatedAt() != null ? u.getCreatedAt().toString() : null);
            list.add(m);
        }
        return ResponseEntity.ok(R.ok("users", list));
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserVO.UserSaveVO vo,
                                                           @RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        checkAdmin(auth);
        User created = userService.create(vo);
        return ResponseEntity.ok(R.ok("id", created.getId()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id,
                                                            @RequestBody UserVO.UserSaveVO vo,
                                                            @RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        checkAdmin(auth);
        userService.update(id, vo);
        return ResponseEntity.ok(R.ok());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id,
                                                            @RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        checkAdmin(auth);
        userService.delete(id);
        return ResponseEntity.ok(R.ok());
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody UserVO.ChangePasswordVO vo,
                                                                @RequestHeader(value = "Authorization", defaultValue = "") String auth) {
        User me = userService.getByToken(auth.replace("Bearer ", ""));
        if (me == null) throw new RuntimeException("未登录");
        userService.changePassword(me.getId(), vo);
        return ResponseEntity.ok(R.ok());
    }

    // ==================== 辅助方法 ====================


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleError(RuntimeException e) {
        return ResponseEntity.badRequest().body(R.error(e.getMessage()));
    }
}
