package ru.tfs.service.metrics;

import java.time.LocalDateTime;

public class MethodInvocationMetric {
    private String method;

    private LocalDateTime invocationTime;

    private Integer totalTime;

    public MethodInvocationMetric(String method, LocalDateTime invocationTime, Integer totalTime) {
        this.method = method;
        this.invocationTime = invocationTime;
        this.totalTime = totalTime;
    }

    public String getMethod() {
        return method;
    }

    public LocalDateTime getInvocationTime() {
        return invocationTime;
    }

    public Integer getTotalTime() {
        return totalTime;
    }
}
