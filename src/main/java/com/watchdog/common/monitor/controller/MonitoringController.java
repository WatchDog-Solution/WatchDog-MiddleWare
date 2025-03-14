package com.watchdog.common.monitor.controller;

import com.watchdog.common.monitor.domain.dto.request.ServerStatusRequestDto;
import com.watchdog.common.monitor.domain.dto.response.ServerStatusResponseDto;
import com.watchdog.common.monitor.application.MonitoringService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    // 의존성 주입
    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    // POST 요청으로 서버 상태 정보 조회
    @PostMapping("/status")
    public ServerStatusResponseDto getServerStatus(@RequestBody ServerStatusRequestDto requestDto) {
        return monitoringService.getServerStatus(requestDto);
    }
}
