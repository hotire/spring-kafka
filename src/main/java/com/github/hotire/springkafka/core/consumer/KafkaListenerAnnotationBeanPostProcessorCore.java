package com.github.hotire.springkafka.core.consumer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.config.MultiMethodKafkaListenerEndpoint;

/**
 * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor
 */
public class KafkaListenerAnnotationBeanPostProcessorCore<K, V> {
    private BeanFactory beanFactory;
    private final KafkaListenerEndpointRegistrarCore registrar = new KafkaListenerEndpointRegistrarCore();

    /**
     * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    /**
     * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor#processMultiMethodListeners(Collection, List, Object, String)
     */
    private void processMultiMethodListeners(Collection<KafkaListener> classLevelListeners, List<Method> multiMethods,
                                             Object bean, String beanName) {
        List<Method> checkedMethods = new ArrayList<>();
        Method defaultMethod = null;
        for (KafkaListener classLevelListener : classLevelListeners) {
            MultiMethodKafkaListenerEndpoint<K, V> endpoint =
                    new MultiMethodKafkaListenerEndpoint<>(checkedMethods, defaultMethod, bean);
            processListener(endpoint, classLevelListener, bean, bean.getClass(), beanName);
        }
    }

    /**
     * @see org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor#processListener(MethodKafkaListenerEndpoint, KafkaListener, Object, Object, String)
     */
    protected void processListener(MethodKafkaListenerEndpoint<?, ?> endpoint, KafkaListener kafkaListener,
                                   Object bean, Object adminTarget, String beanName) {

        KafkaListenerContainerFactory<?> factory = this.beanFactory.getBean(kafkaListener.containerFactory(), KafkaListenerContainerFactory.class);
        this.registrar.registerEndpoint(endpoint, factory);
    }
}
