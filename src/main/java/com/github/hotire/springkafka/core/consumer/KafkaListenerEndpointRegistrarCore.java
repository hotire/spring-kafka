package com.github.hotire.springkafka.core.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MultiMethodKafkaListenerEndpoint;
import org.springframework.validation.Validator;

/**
 * @see KafkaListenerEndpointRegistrar
 */
public class KafkaListenerEndpointRegistrarCore implements InitializingBean {

    private KafkaListenerEndpointRegistry endpointRegistry;

    private Validator validator;

    private final List<KafkaListenerEndpointDescriptorCore> endpointDescriptors = new ArrayList<>();

    /**
     * @see KafkaListenerEndpointRegistrar#registerEndpoint(KafkaListenerEndpoint, KafkaListenerContainerFactory)
     */
    public void registerEndpoint(KafkaListenerEndpoint endpoint, KafkaListenerContainerFactory<?> factory) {
        KafkaListenerEndpointDescriptorCore descriptor = new KafkaListenerEndpointDescriptorCore(endpoint, factory);
        endpointDescriptors.add(descriptor);
    }

    /**
     * @see KafkaListenerEndpointRegistrar#registerAllEndpoints()
     */
    public void registerAllEndpoints() {
        synchronized (this.endpointDescriptors) {
            for (KafkaListenerEndpointDescriptorCore descriptor : this.endpointDescriptors) {
                if (descriptor.endpoint instanceof MultiMethodKafkaListenerEndpoint
                    && this.validator != null) {
                    ((MultiMethodKafkaListenerEndpoint) descriptor.endpoint).setValidator(this.validator);
                }
                this.endpointRegistry.registerListenerContainer(
                        descriptor.endpoint, resolveContainerFactory(descriptor));
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        registerAllEndpoints();
    }

    /**
     * @see KafkaListenerEndpointRegistrar#resolveContainerFactory(org.springframework.kafka.config.KafkaListenerEndpointRegistrar.KafkaListenerEndpointDescriptor;)
     */
    private KafkaListenerContainerFactory<?> resolveContainerFactory(KafkaListenerEndpointDescriptorCore descriptor) {
        return descriptor.containerFactory;
    }

    private static final class KafkaListenerEndpointDescriptorCore {

        private final KafkaListenerEndpoint endpoint;

        private final KafkaListenerContainerFactory<?> containerFactory;

        private KafkaListenerEndpointDescriptorCore(KafkaListenerEndpoint endpoint,
                                                    KafkaListenerContainerFactory<?> containerFactory) {
            this.endpoint = endpoint;
            this.containerFactory = containerFactory;
        }

    }
}
