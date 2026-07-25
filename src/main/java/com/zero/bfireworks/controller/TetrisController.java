package com.zero.bfireworks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zero.bfireworks.service.TetrisService;
import com.zero.bfireworks.vo.TetrisVO;
import com.zero.bfireworks.entity.Tetris;

import jakarta.annotation.Resource;

package com.zero.bfireworks.controller;

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
public class TetrisController {
    @Resource
    private TetrisService tetrisService;
    @PostMapping("/tetris/{id}")
    public ResponseEntity<Map<String,Object>> CreateTetris(@PathVariable Integer id,@RequestBody TetrisVO.TetrissaveVO vo) {
        Tetris tetris = tetrisService.create(vo.getName(),vo.getScore(),id);
        return ResponseEntity.ok(R.ok("tetris",tetris));
    }
    @PutMapping("/tetris/{id}")
    public ResponseEntity<Map<String,Object>> UpdateTetris(@PathVariable Integer id,@RequestBody TetrisVO.TetrissaveVO vo) {
        Tetris tetris = tetrisService.update(vo.getName(),vo.getScore(),id);
        return ResponseEntity.ok(R.ok("tetris",tetris));
    }
    @GetMapping("/tetris")
    public ResponseEntity<Map<String,Object>> ListTetris() {
        List<Tetris> list = tetrisService.list();
        return ResponseEntity.ok(R.ok("tetris",list));
    }
    @GetMapping("/tetris/{id}")
    public ResponseEntity<Map<String,Object>> GetTetris(@PathVariable Integer id) {
        Tetris tetris = tetrisService.getById(id);
        return ResponseEntity.ok(R.ok("tetris",tetris));
    }
    @DeleteMapping("/tetris/{id}")
    public ResponseEntity<Map<String,Object>> DeleteTetris(@PathVariable Integer id,@RequestHeader(value = "Authorization" , defaultValue = "") String auth) {
        checkAdmin(auth);
        tetrisService.delete(id);
        return ResponseEntity.ok(R.ok());
    }
}
