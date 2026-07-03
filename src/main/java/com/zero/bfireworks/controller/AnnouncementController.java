package com.zero.bfireworks.controller;


import com.zero.bfireworks.entity.Announcement;
import com.zero.bfireworks.entity.User;
import com.zero.bfireworks.service.AnnouncementService;
import com.zero.bfireworks.service.impl.UserServiceImpl;
import com.zero.bfireworks.util.R;
import com.zero.bfireworks.vo.AnnouncementVO;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.zero.bfireworks.util.EveryCanUse.checkAdmin;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;
    @Resource
    private UserServiceImpl userService;
    @GetMapping("/announcements")
    public ResponseEntity<Map<String, Object>> list() {
        List<Announcement> list = announcementService.listAll();
        return ResponseEntity.ok(R.ok("announcements",list));
    }
    @PostMapping("/announcements")
    public ResponseEntity<Map<String, Object>> create(@RequestBody AnnouncementVO.SaveVO vo,@RequestHeader(value = "Authorization" , defaultValue = "") String auth) {
        checkAdmin(auth);
        Announcement a = announcementService.create(vo.getTitle(), vo.getContent(), vo.getPinned());
        return ResponseEntity.ok(R.ok("id", a.getId()));
    }
    @PutMapping("/announcements/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody AnnouncementVO.SaveVO vo,@RequestHeader(value = "Authorization" , defaultValue = "") String auth) {
        checkAdmin(auth);
        Announcement a = announcementService.update(id, vo.getTitle(), vo.getContent(), vo.getPinned());
        return ResponseEntity.ok(R.ok("id", a.getId()));
    }
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id,@RequestHeader(value = "Authorization" , defaultValue = "") String auth) {
        checkAdmin(auth);
        announcementService.delete(id);
        return ResponseEntity.ok(R.ok());
    }


}
