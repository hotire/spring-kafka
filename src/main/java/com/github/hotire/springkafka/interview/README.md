# Kafka

Kafka는 Distributed Streaming Platform 분산 스트리밍 플랫폼 

성능이 뛰어나고 원하는 기간만큼 안정적으로 데이터를 저장한다.  또한 모든 기능이 분산되어 있어 확장성과 내결함성(fault tolerance)이 뛰어나다. 

이러한 특징 덕분에 Kafka는 기존 메시징 시스템(Active MQ, Rabbit MQ)을 대체하기도 하고 시스템 로그를 모으거나 데이터 가공을 위해 파이프라인을 구성할 때 등 다양한 경우에 사용된다. 


### Broker

하나의 카프카 서버를 브로커라고 한다. 

브로커는 프로듀서로부터 메시지를 수신하고 오프셋을 지정한 후 해당 메시지를 디스크에 저장한다. 또한 컨슈머의 파티션 읽기 요청에 응답하고 디스크에 수록된 메시지를 전송한다. 

### 클러스터 

브로커는 클러스터의 일부로 동작하도록 설계되었다. 즉 여러 개의 브로커가 하나의 클러스터에 포함될 수 있다. 

클러스터의 여러 브로커 중 하나는 자동으로 컨트롤러의 기능을 수행한다. 

### 컨트롤러

브로커에게 담당 파티션을 할당하고 모니터링한다. 

### 리더 

각 파티션은 한 브로커가 소유하며 그 브로커를 리더라고 한다. 

같은 파티션이 여러 브로커에 지정될 수 있고 파티션이 복제된다. (장애 방지)


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

전송할 record, 전송 완료후 콜백을 지정해서 send를 호출하면 

Serialization, Partitioning, Compression 작업이 이루어지고 RecordAccumulator에 Record가 저장된다.

RecordAccumulator batches라는 Map을 가지고 있는데, 이 Map의 Key는 TopicPartition이고, Value는 Deque<RecordBatch>이다.

- Deque 구현체는 ArrayDeque이다. 
: cache 지역성이 더 좋다. 고정된 크기를 사용하는 batches 에서 배열의 단점인 공간 비효율, 배열 재배치가 일어날수 없다. 


- Drain : 
drain()에서는 먼저 각 Broker Node에 속하는 TopicPartition 목록을 얻어온다. 그리고 각 Node에 속한 TopicPartition을 보면서 Deque First쪽의 RecordBatch 하나를 꺼내서 RecordBatch List에 추가한다. 이렇게 Node 단위로 RecordBatch List가 max.request.size 설정값을 넘지 않을 때까지 모은다. 
모든 Node에 이 동작을 반복하면 Node별로 전송할 RecordBatch List가 모인다.



### Sender

- maxRequestSize : sender 에서 recordAccumulator로 부터 해당 크기 만큼 drain한다. 

ProduceRequest는 InFlightRequests라는 Node별 Deque에 먼저 저장된다.


### InFlightRequests

- max.in.flight.requests.per.connection : KafkaProducer Client가 하나의 Broker로 동시에 전송할 수 있는 요청 수를 의미한다.

Broker는 하나의 Connection에 대해서 요청이 들어온 순서대로 처리해서 응답한다. 
응답의 순서가 보장되기 때문에, KafkaProducer Client는 Broker로부터 응답이 오면 항상 InFlightRequests Deque의 가장 오래된 요청을 완료 처리한다.

### KafkaProducer

~~~java
this.ioThread = new KafkaThread(ioThreadName, this.sender, true);
this.ioThread.start();
~~~

### KafkaTemplate

- SettableListenableFuture

### ProducerFactory

TODO...

## Kafka Transaction

- https://gunju-ko.github.io/kafka/spring-kafka/2018/03/31/Spring-KafkaTransaction.html
- https://bestugi.tistory.com/44
