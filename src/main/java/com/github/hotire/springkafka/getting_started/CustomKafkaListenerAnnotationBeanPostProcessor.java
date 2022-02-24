package com.github.hotire.springkafka.getting_started;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor
 */
@Getter
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomKafkaListenerAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;
    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();
    private BeanExpressionContext expressionContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
            this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
        }
    }

    private final Map<String, String > idByTopic = new HashMap<>();

    /**
     * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, Set<KafkaListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                                                                                            (MethodIntrospector.MetadataLookup<Set<KafkaListener>>) method -> {
                                                                                                Set<KafkaListener> listenerMethods = findListenerAnnotations(method);
                                                                                                return (!listenerMethods.isEmpty() ? listenerMethods : null);
                                                                                            });

        if (!annotatedMethods.isEmpty()) {
            log.info("annotatedMethods : {}" , annotatedMethods);

            annotatedMethods.values().forEach(annotations -> annotations.forEach(annotation -> {
                try {
                    for (String topic : resolveTopics(annotation)) {
                        idByTopic.put(topic + resolveExpressionAsString(annotation.groupId(), "groupId"), annotation.id());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
             }
            ));
        }

        return bean;
    }

    private String resolveExpressionAsString(String value, String attribute) {
        Object resolved = resolveExpression(value);
        if (resolved instanceof String) {
            return (String) resolved;
        }
        else if (resolved != null) {
            throw new IllegalStateException("The [" + attribute + "] must resolve to a String. "
                                            + "Resolved to [" + resolved.getClass() + "] for [" + value + "]");
        }
        return null;
    }

    private String[] resolveTopics(KafkaListener kafkaListener) {
        String[] topics = kafkaListener.topics();
        List<String> result = new ArrayList<>();
        if (topics.length > 0) {
            for (String topic1 : topics) {
                Object topic = resolveExpression(topic1);
                resolveAsString(topic, result);
            }
        }
        return result.toArray(new String[0]);
    }

    private void resolveAsString(Object resolvedValue, List<String> result) {
        if (resolvedValue instanceof String[]) {
            for (Object object : (String[]) resolvedValue) {
                resolveAsString(object, result);
            }
        }
        else if (resolvedValue instanceof String) {
            result.add((String) resolvedValue);
        }
        else if (resolvedValue instanceof Iterable) {
            for (Object object : (Iterable<Object>) resolvedValue) {
                resolveAsString(object, result);
            }
        }
        else {
            throw new IllegalArgumentException(String.format(
                    "@KafKaListener can't resolve '%s' as a String", resolvedValue));
        }
    }

    private Object resolveExpression(String value) {
        return this.resolver.evaluate(resolve(value), this.expressionContext);
    }

    private String resolve(String value) {
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }
    private Set<KafkaListener> findListenerAnnotations(Method method) {
        Set<KafkaListener> listeners = new HashSet<>();
        KafkaListener ann = AnnotatedElementUtils.findMergedAnnotation(method, KafkaListener.class);
        if (ann != null) {
            listeners.add(ann);
        }
        KafkaListeners anns = AnnotationUtils.findAnnotation(method, KafkaListeners.class);
        if (anns != null) {
            listeners.addAll(Arrays.asList(anns.value()));
        }
        return listeners;
    }

}
