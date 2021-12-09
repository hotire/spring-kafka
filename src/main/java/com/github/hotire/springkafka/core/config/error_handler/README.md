# ErrorHandler


https://docs.spring.io/spring-kafka/reference/html/#error-handlers

기본으로 설정하지 않으면 LoggingErrorHandler

- KafkaMessageListenerContainer.ListenerConsumer 기본 전략 
~~~
protected ErrorHandler determineErrorHandler(GenericErrorHandler<?> errHandler) {
			return errHandler != null ? (ErrorHandler) errHandler
					: this.transactionManager != null ? null : new LoggingErrorHandler();
		}
~~~


## SeekToCurrentErrorHandler


## Statefult Retry

Statefult Retry는 재시도가 가능함에도 불구하고 예외를 던진다.



## Retry

- https://gunju-ko.github.io/kafka/spring-kafka/2018/04/16/Spring-Kafka-Retry.html