package com.watchdog.common.monitor.domain.dto.response;

public class ServerStatusResponseDto {
    private double systemLoad;
    private long usedMemory;
    private int cpuCores;

    public ServerStatusResponseDto(double systemLoad, long usedMemory, int cpuCores) {
        this.systemLoad = systemLoad;
        this.usedMemory = usedMemory;
        this.cpuCores = cpuCores;
    }

    public double getSystemLoad() {
        return systemLoad;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public int getCpuCores() {
        return cpuCores;
    }
}
