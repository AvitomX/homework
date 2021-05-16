package ru.tfs.autoconfigure;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tfs.service.TimedBeanPostProcessor;
import ru.tfs.service.metrics.MetricStatProvider;
import ru.tfs.service.metrics.MetricStatProviderImpl;


@Configuration
@ConditionalOnClass(TimedBeanPostProcessor.class)
@ConditionalOnProperty(prefix = "metrics", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(TimedProperties.class)
public class TimedAutoconfigure {

    private final TimedProperties properties;

    public TimedAutoconfigure(TimedProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MetricStatProvider getMetricStatProvider(){
        return new MetricStatProviderImpl(properties.getLimit());
    }

    @Bean
    public BeanPostProcessor getTimedBeanPostProcessor(){
        return new TimedBeanPostProcessor(getMetricStatProvider());
    }
}
