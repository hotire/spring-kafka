# Kafka

Kafka는 Distributed Streaming Platform

## KafkaProducer Client Internals

https://d2.naver.com/helloworld/6560422

### KafkaProducer

사용자가 직접 사용하는 클래스입니다. 

전송할 record, 전송 완료후 콜백을 지정해서 send를 호출하면 

Serialization, Partitioning, Compression 작업이 이루어지고 RecordAccumulator에 Record가 저장된다.

- Serialization : Record의 Key, Value는 지정된 Serializer에 의해서 Byte Array로 변환된다
- Partitioning : Topic은 여러 개의 Partition으로 저장되잖아요. record를 지정된 Partitioner에에 의해서 파티션이 결정하는데 
지정하지 않으면 DefaultPartitioner가 사용됩니다. (Partitioner는 기본적으로 Record를 받아서 Partition Number를 반환하는 역할을 한다.)
    - Key 값이 있는 경우 Key 값의 Hash 값을 이용해서 Partition을 할당한다.
    - Key 값이 없는 경우 Round-Robin 방식으로 Partition이 할당된다.
- Compression : Record는 압축을 함으로써 네트워크 전송 비용도 줄일 수 있고 저장 비용도 줄일 수 있다. Record는 RecordAccumulator에 저장될 때 바로 압축되어 저장된다. 기본전략은 none
gzip, snappy, lz4

    
    




### RecordAccumulator

### Sender

