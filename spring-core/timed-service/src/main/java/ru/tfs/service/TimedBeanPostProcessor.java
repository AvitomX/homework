package ru.tfs.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import ru.tfs.service.metrics.MetricStatProvider;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TimedBeanPostProcessor implements BeanPostProcessor {
    private Map<String , Class<?>> classesForProxy = new HashMap<>();
    private MetricStatProvider metricStatProvider;

    public TimedBeanPostProcessor(MetricStatProvider metricStatProvider) {
        this.metricStatProvider = metricStatProvider;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> type = bean.getClass();
        if (type.isAnnotationPresent(Timed.class)) {
            classesForProxy.put(beanName, type);
        } else {
            for (Method method : type.getMethods()) {
                if (method.isAnnotationPresent(Timed.class)) {
                    classesForProxy.put(beanName, type);
                    break;
                }
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> type = classesForProxy.get(beanName);

        if (type != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(type);
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                if (method.getDeclaringClass() == type &&
                        (type.isAnnotationPresent(Timed.class) || method.isAnnotationPresent(Timed.class))) {
                    return getProxy(proxy, method, bean, args);
                } else {
                    return proxy.invoke(bean, args);
                }
            });
            return enhancer.create();
        }

        return bean;
    }

    private Object getProxy(MethodProxy proxy, Method method, Object bean, Object[] args) throws Throwable {
        LocalDateTime dateTime = LocalDateTime.now();
        long before = System.currentTimeMillis();
        Object invoke = proxy.invoke(bean, args);
        long after = System.currentTimeMillis();
        String fullMethodName = bean.getClass().getName() + "." + method.getName();
        metricStatProvider.addInvocationMethodMetric(fullMethodName, dateTime, (int) (after - before));
        return invoke;
    }
}
