# Listener 

KafkaListener annotation bean 등록 과정 

### KafkaListenerAnnotationBeanPostProcessorCore

- postProcessAfterInitialization

- processMultiMethodListeners

- processListener

### KafkaListenerEndpoint

A KafkaListenerEndpoint providing the method to invoke to process an incoming message for this endpoint.

endpoint의 message를 처리 하기위해 메서드를 제공하는 인터페이스.

구현체로 MethodKafkaListenerEndpoint, MultiMethodKafkaListenerEndpoint 사용 


### KafkaListenerEndpointRegistrar

- registerEndpoint

- afterPropertiesSet

- registerAllEndpoints

Helper bean for registering KafkaListenerEndpoint with KafkaListenerEndpointRegistry.

KafkaListenerEndpointRegistry에 KafkaListenerEndpoint 등록하기 위한 helper

### KafkaListenerEndpointRegistry

- registerListenerContainer

Creates the necessary MessageListenerContainer instances for the registered KafkaListenerEndpoint endpoints.

Also manages the lifecycle of the listener containers, in particular within the lifecycle of the application context.


등록된 KafkaListenerEndpoint 끝점에 필요한 MessageListenerContainer 인스턴스를 만듭니다.

Register에서 관리하는 MessageListenerContainer는 Bean이 아니다.