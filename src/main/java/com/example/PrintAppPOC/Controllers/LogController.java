package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.Services.ServiceImpl.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;
    @GetMapping("/getLogs/{start}/{end}")
    @ResponseBody
    public ResponseEntity<List<String>> getLogs(@PathVariable String start, @PathVariable String end) {
        return ResponseEntity.ok(logService.logs(start,end));
    }
    @GetMapping("/recentLogs")
    public ResponseEntity<List<String>> getRecentLogs() {
        List<String> recentLogs = logService.getRecentLogs();
        return ResponseEntity.ok(recentLogs);
    }
    @GetMapping("/logPage")
    public String logPage(){
        return "logFetcher";
    }
}
