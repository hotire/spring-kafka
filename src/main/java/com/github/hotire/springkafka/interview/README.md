# Kafka

Kafka는 Distributed Streaming Platform

## KafkaProducer Client Internals

https://d2.naver.com/helloworld/6560422

### KafkaProducer

사용자가 직접 사용하는 클래스입니다. 

전송할 record, 전송 완료후 콜백을 지정해서 send를 호출하면 

Serialization, Partitioning, Compression 작업이 이루어지고 RecordAccumulator에 Record가 저장된다.

- Serialization : Record의 Key, Value는 지정된 Serializer에 의해서 Byte Array로 변환된다
- Partitioning : 

### RecordAccumulator

### Sender

