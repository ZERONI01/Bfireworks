package com.zero.bfireworks.controller;


import com.zero.bfireworks.entity.Messages;
import com.zero.bfireworks.service.impl.MessagesServiceImpl;
import com.zero.bfireworks.service.impl.UserServiceImpl;
import com.zero.bfireworks.util.R;
import com.zero.bfireworks.vo.MessagesVO;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.zero.bfireworks.util.EveryCanUse.checkAdmin;

@RestController
@RequestMapping("/api")
public class MessagesController {
    @Resource
    private MessagesServiceImpl messagesService;
    @Resource
    private UserServiceImpl userService;

    @GetMapping("/messages")
    public ResponseEntity<Map<String, Object>> listMessages(@RequestParam(defaultValue = "message") String type) {
        List<Messages> list;
        if ("pending".equals(type)) {
            list = messagesService.listPending();
        } else {
            list = messagesService.listByType(type);
        }
        return ResponseEntity.ok(R.ok("messages", list));
    }

    @PostMapping("/messages")
    public ResponseEntity<Map<String, Object>> create(@RequestBody MessagesVO.MessagesSaveVO vo) {
        Messages m = messagesService.create(vo.getContent(), vo.getAuthor(), vo.getType());
        return ResponseEntity.ok(R.ok("id", m.getId()));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id, @RequestHeader(value = "Authorization", defaultValue = "")String auth) {
        checkAdmin( auth);
        messagesService.delete(id);
        return ResponseEntity.ok(R.ok());
    }

    @PutMapping("/messages/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody MessagesVO.MessagesSaveVO vo, @RequestHeader(value = "Authorization", defaultValue = "")String auth) {
        checkAdmin(auth);
        messagesService.update(id, vo.getContent(), vo.getAuthor(), vo.getType());
        return ResponseEntity.ok(R.ok());
    }
    @PutMapping("/messages/{id}/approve")
    public ResponseEntity<Map<String, Object>> approve(@PathVariable Integer id, @RequestHeader(value = "Authorization", defaultValue = "")String auth) {
        checkAdmin(auth);
        messagesService.approve(id);
        return ResponseEntity.ok(R.ok());
    }



}
