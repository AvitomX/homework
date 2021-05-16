package ru.tfs.service.metrics;

public class MethodMetricStat {
    private String methodName;

    private Integer invocationsCount;

    private Integer minTime;

    private Integer averageTime;

    private Integer maxTime;

    public MethodMetricStat(String methodName, Integer invocationsCount, Integer minTime, Integer averageTime, Integer maxTime) {
        this.methodName = methodName;
        this.invocationsCount = invocationsCount;
        this.minTime = minTime;
        this.averageTime = averageTime;
        this.maxTime = maxTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public Integer getInvocationsCount() {
        return invocationsCount;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public Integer getAverageTime() {
        return averageTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    @Override
    public String toString() {
        return "MethodMetricStat{" +
                "methodName='" + methodName + '\'' +
                ", invocationsCount=" + invocationsCount +
                ", minTime=" + minTime +
                ", averageTime=" + averageTime +
                ", maxTime=" + maxTime +
                '}';
    }
}
