package com.zero.bfireworks.controller;

import com.zero.bfireworks.entity.Jump;
import com.zero.bfireworks.service.JumpService;
import com.zero.bfireworks.util.R;
import com.zero.bfireworks.vo.JumpVO;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.zero.bfireworks.util.EveryCanUse.checkAdmin;

@RestController
@RequestMapping("/api")
public class JumpController {
    @Resource
    private JumpService jumpservice;
    @PostMapping("/jump/{id}")
    public ResponseEntity<Map<String, Object>> CreateJump(@PathVariable Integer id, @RequestBody JumpVO.JumpSaveVO vo) {
        Jump jump = jumpservice.create(vo.getName(), vo.getScore(), id);
        return ResponseEntity.ok(R.ok("jump", jump));
    }
    @PutMapping("/jump/{id}")
    public ResponseEntity<Map<String, Object>> UpdateJump(@PathVariable Integer id, @RequestBody JumpVO.JumpSaveVO vo) {
        Jump jump = jumpservice.update(id, vo.getName(), vo.getScore());
        return ResponseEntity.ok(R.ok("jump", jump));
    }
    @GetMapping("/jump")
    public ResponseEntity<Map<String, Object>> ListJump() {
        List<Jump> list = jumpservice.list();
        return ResponseEntity.ok(R.ok("jump", list));
    }
    @DeleteMapping("/jump/{id}")
    public ResponseEntity<Map<String, Object>> DeleteJump(@PathVariable Integer id,@RequestHeader(value = "Authorization" , defaultValue = "") String auth) {
        checkAdmin(auth);
        jumpservice.delete(id);
        return ResponseEntity.ok(R.ok());
    }
    @GetMapping ("/jump/{id}")
    public ResponseEntity<Map<String, Object>> GetJump(@PathVariable Integer id) {
        Jump jump = jumpservice.getById(id);
        return ResponseEntity.ok(R.ok("jump", jump));
    }


}
