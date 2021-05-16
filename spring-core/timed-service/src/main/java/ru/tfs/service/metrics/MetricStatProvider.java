package ru.tfs.service.metrics;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricStatProvider {
    void addInvocationMethodMetric(String methodName, LocalDateTime invocationTime, Integer totalTime);

    List<MethodMetricStat> getTotalStat();

    List<MethodMetricStat> getTotalStatForPeriod(LocalDateTime from,
                                                 LocalDateTime to);

    MethodMetricStat getTotalStatByMethod(String method);

    MethodMetricStat getTotalStatByMethodForPeriod(String method,
                                                   LocalDateTime from,
                                                   LocalDateTime to);
}
