package com.watchdog.common.monitor.application;

import com.watchdog.common.monitor.domain.dto.request.ServerStatusRequestDto;
import com.watchdog.common.monitor.domain.dto.response.ServerStatusResponseDto;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    private final MonitoringSubService monitoringSubService;

    public MonitoringService(MonitoringSubService monitoringSubService) {
        this.monitoringSubService = monitoringSubService;
    }

    public ServerStatusResponseDto getServerStatus(ServerStatusRequestDto requestDto) {
        double systemLoad = monitoringSubService.getSystemLoad();
        long usedMemory = monitoringSubService.getUsedMemory();
        int cpuCores = monitoringSubService.getCpuCores();

        return new ServerStatusResponseDto(systemLoad, usedMemory, cpuCores);
    }
}