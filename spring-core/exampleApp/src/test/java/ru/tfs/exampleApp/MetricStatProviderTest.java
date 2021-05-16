package ru.tfs.exampleApp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tfs.exampleApp.service.ApiService;
import ru.tfs.service.metrics.MethodMetricStat;
import ru.tfs.service.metrics.MetricStatProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricStatProviderTest {
    @Autowired
    private ApiService apiService;

    @Autowired
    private MetricStatProvider provider;

    @Test
    public void annotationForMethodsTest(){
        apiService.get();
        List<MethodMetricStat> totalStat = provider.getTotalStat();

        Assert.assertFalse(totalStat.contains("ru.tfs.exampleApp.repo.ApiRepoImpl.find"));
    }

    @Test
    public void getStatByMethodTest(){
        String methodName = "ru.tfs.exampleApp.service.ApiServiceImpl.get";
        MethodMetricStat totalStatByMethod = provider.getTotalStatByMethod(methodName);
        int count = 0;
        if (totalStatByMethod != null) {
            count = totalStatByMethod.getInvocationsCount();
        }
        apiService.get();
        MethodMetricStat statByMethod = provider.getTotalStatByMethod(methodName);

        Assert.assertTrue(methodName.equals(statByMethod.getMethodName()));
        Assert.assertTrue(statByMethod.getInvocationsCount() == count + 1);
    }

    @Test
    public void getStatForPeriodTest(){
        LocalDateTime before = LocalDateTime.now().minusSeconds(5);
        apiService.delete(UUID.randomUUID());
        List<MethodMetricStat> statForPeriod = provider.getTotalStatForPeriod(before, LocalDateTime.now());

        Assert.assertTrue(statForPeriod.size() >= 2);
    }

    @Test
    public void getStatForPeriodTestByMethod(){
        String methodName = "ru.tfs.exampleApp.service.ApiServiceImpl.delete";
        LocalDateTime before = LocalDateTime.now().minusSeconds(5);
        apiService.delete(UUID.randomUUID());
        MethodMetricStat statMethodForPeriod = provider.getTotalStatByMethodForPeriod(
                methodName,
                before,
                LocalDateTime.now()
        );

        Assert.assertTrue(methodName.equals(statMethodForPeriod.getMethodName()));
        Assert.assertTrue(statMethodForPeriod.getInvocationsCount() >= 1);
    }

}