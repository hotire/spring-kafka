# Listener 

KafkaListener annotation bean 등록 과정 

### KafkaListenerAnnotationBeanPostProcessorCore

- postProcessAfterInitialization

- processMultiMethodListeners

- processListener

### KafkaListenerEndpointRegistrar

- registerEndpoint

- afterPropertiesSet

- registerAllEndpoints

Helper bean for registering KafkaListenerEndpoint with KafkaListenerEndpointRegistry.


### KafkaListenerEndpointRegistry

- registerListenerContainer

Creates the necessary MessageListenerContainer instances for the registered KafkaListenerEndpoint endpoints.

