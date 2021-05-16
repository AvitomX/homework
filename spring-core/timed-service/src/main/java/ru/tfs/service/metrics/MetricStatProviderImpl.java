package ru.tfs.service.metrics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MetricStatProviderImpl implements MetricStatProvider {
    private Map<String, List<MethodInvocationMetric>> metrics = new ConcurrentHashMap<>();
    private final int limit;

    public MetricStatProviderImpl(int limit) {
        this.limit = limit;
    }


    @Override
    public void addInvocationMethodMetric(String methodName, LocalDateTime invocationTime, Integer totalTime) {
        MethodInvocationMetric metric = new MethodInvocationMetric(methodName, invocationTime, totalTime);

        metrics.compute(methodName, (k, v) -> {
            List<MethodInvocationMetric> invocationMetricList =
                    (v == null) ? new LinkedList<MethodInvocationMetric>() : v;

            if (invocationMetricList.size() >= this.limit) {
                invocationMetricList.remove(0);
            }
            invocationMetricList.add(metric);
            return invocationMetricList;
        });
    }

    private MethodMetricStat getMethodStat(List<MethodInvocationMetric> list) {
        if (list.isEmpty())
            return null;

        String name = list.get(0).getMethod();
        int count = list.size();

        int min = Integer.MAX_VALUE;
        int max = 0;
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            int metricTotalTime = list.get(i).getTotalTime();
            min = Math.min(metricTotalTime, min);
            max = Math.max(metricTotalTime, max);
            sum += metricTotalTime;
        }

        MethodMetricStat metricStat = new MethodMetricStat(name, count, min, sum / count, max);
        return metricStat;
    }

    @Override
    public List<MethodMetricStat> getTotalStat() {
        List<MethodMetricStat> metricStats = new ArrayList<>();
        metrics.forEach((s, list) -> metricStats.add(getMethodStat(list)));

        return metricStats;
    }

    @Override
    public List<MethodMetricStat> getTotalStatForPeriod(LocalDateTime from, LocalDateTime to) {
        List<MethodMetricStat> metricStats = new ArrayList<>();
        for (Map.Entry<String, List<MethodInvocationMetric>> entry : metrics.entrySet()) {
            List<MethodInvocationMetric> list = entry.getValue().stream()
                    .filter(v -> v.getInvocationTime().isAfter(from) && v.getInvocationTime().isBefore(to))
                    .collect(Collectors.toList());
            metricStats.add(getMethodStat(list));
        }

        return metricStats;
    }

    @Override
    public MethodMetricStat getTotalStatByMethod(String method) {
        if (metrics.containsKey(method))
            return getMethodStat(metrics.get(method));
        return null;
    }

    @Override
    public MethodMetricStat getTotalStatByMethodForPeriod(String method, LocalDateTime from, LocalDateTime to) {
        if (metrics.containsKey(method)) {
            List<MethodInvocationMetric> list = metrics.get(method);
            return getMethodStat(
                    list.stream()
                            .filter(v -> v.getInvocationTime().isAfter(from) && v.getInvocationTime().isBefore(to))
                            .collect(Collectors.toList())
            );
        }

        return null;
    }
}
