package com.watchdog.common.monitor.domain.dto.request;

public class ServerStatusRequestDto {
    private String requestType; // 예: "CURRENT", "HISTORICAL"
    private long startTime; // 조회 시작 시간 (timestamp)
    private long endTime;   // 조회 종료 시간 (timestamp)

    public ServerStatusRequestDto() {}

    public ServerStatusRequestDto(String requestType, long startTime, long endTime) {
        this.requestType = requestType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}