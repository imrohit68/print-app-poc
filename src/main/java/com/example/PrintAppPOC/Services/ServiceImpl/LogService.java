package com.example.PrintAppPOC.Services.ServiceImpl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LogService {
    private static final int RECENT_LOGS_COUNT = 20;
    public  List<String> logs (String start , String end) {
        try (Stream<String> lines = Files.lines(Paths.get("application.log"))) {
            return lines.filter((s -> {
                String timeStamp = s.substring(0,19);
                LocalDateTime logTime = LocalDateTime.parse(timeStamp);
                LocalDateTime startTime = LocalDateTime.parse(start);
                LocalDateTime endTime = LocalDateTime.parse(end);
                return (logTime.isEqual(startTime)||logTime.isAfter(startTime)) && (logTime.isEqual(endTime)||logTime.isBefore(endTime));
            })).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getRecentLogs() {
        try (Stream<String> lines = Files.lines(Paths.get("application.log"))) {
            return lines
                    .skip(Math.max(0, Files.lines(Paths.get("application.log")).count() - RECENT_LOGS_COUNT))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
