# spring-kafka
study 

![kafka](/doc/img/kafka.png)

### Getting Started 

- https://codenotfound.com/spring-kafka-consumer-producer-example.html

- https://blog.naver.com/gngh0101/221763474986

### Testing Kafka and Spring Boot

- https://www.baeldung.com/spring-boot-kafka-testing

## Core Concepts

### Producer
메시지 생산자 

### Consumer
메시지 소비자

### Broker
카프카 서버

### Cluster 
브로커의 묶음

### Topic
메시지 종류, 데이터가 들어갈 수 있는 공간 

### Partitions
topic를 나누는 단위, 메시지를 분산 저장한다. 

Kafka는 topic의 Partition이라는 단위로 쪼개서 클러스터의 각 서버들에 분산되어 저장됩니다. 
만약 고가용성을 위해서 복제 설정을 하게 되면 이것 또한 Partition 단위로 각 서버들에 분산되어 복제되고 장애가 발생하면 Partition 단위로 fail over가 수행됩니다.
각 Partition은 0부터 1씩 증가하는 offset 값을 메시지에 부여하는데 이 값은 각 Partition내에서 메세지를 식별하는 ID로 사용되게 됩니다. offset 값은 Partition마다 별도로 관리되므로 topic 내에서 메시지를 식별할 때는 Partition 번호와 offset 값을 함께 사용합니다.

3개의 Broker로 이루어진 Cluster에서는 하나의 topic이 3개의 Partition 형태로 분산되어 저장되게 됩니다.
Producer가 메시지를 실제로 어떤 Partition으로 전송할지는 사용자가 구현한 Partition 분배 알고리즘에 의해 결정되게 되는데요.
예를 들어 라운드로빈 방식으로 Partition 분배 알고리즘을 구현하여 각 Partition에 메시지를 균등하게 분배하도록 하거나 메시지의 키를 활용하여 알파벳 앞자리로 시작하는 키를 가진 메시지는 한곳에 넣는 방식으로 구성도 가능합니다.
다른 적절하게 분배하는 방식에 대해서는 CRC32값을 Partition 수로 연산을 하여서 동일한 ID에 대한 메시지는 동일한 Partition에 할당되도록 구성도 가능합니다. 공식. CRC32(ID) % Partition 

### Offset
파티션 내에서 각 메시자가 가지는 unique id

실제 Offset과 Partition 번호가 같이 사용

### Log
1개의 메시지 

### How to work

- zookeeper가 kafka의 상태와 클러스터 관리를 해준다.

- topic에 producer가 메세지를 발행해놓으면 consumer가 필요할때 해당 메세지를 가져가 소비한다.
  (여기서 카프카로 발행된 메세지들은 consumer가 메세지를 소비한다고 해서 없어지는게 아니라 카프카 설정(default 7일)에 의해 삭제된다.)

## Consumer

### Lag
프로듀서의 오프셋과 컨슈머의 오프셋간의 차이다. 

파티션이 여러개면 당연히 Lag도 여러개다. 그 중 높은 숫자의 lag을 records-lag-max라고 부른다.

## Consumer Group

https://www.popit.kr/kafka-consumer-group/

카프카에서는 하나의 파티션에 대해 컨슈머 그룹내 하나의 컨슈머 인스턴스만 접근할 수 있다. 
-> 파티션에 대해 한명의 reader만 허용하여 데이터를 순서대로 읽어갈 수 있게 하기 위함(ordering 보장) 파티션 수보다 컨슈머 그룹의 인스턴스 수가 많을 수 없습니다.


## Config

### Consumer 

- request.timeout.ms : 요청에 대해 응답을 기다리는 최대 시간 (default: 305000)
- max.poll.records: 단일 호출 poll()에 대해 최대 레코드 수를 조정. 이 옵션을 통해 app이 폴링 루프에서 데이터 양을 조정할 수 있음 (default: 500)
- max.poll.interval.ms: 하트비트를 통해 살아는 있으나 실제 메세지를 가져가지 않을 경우. 주기적으로 poll을 호출하지 않으면 장애라고 판단하고 컨슈머 그룹에서 제외 (default: 300,000)


## Reactive

- Reactor-Kafka
https://projectreactor.io/docs/kafka/release/reference/

- Getting Started
https://github.com/reactor/reactor-kafka/tree/master/reactor-kafka-samples/src/main/java/reactor/kafka/samples


## KafkaProducer Client Internals

https://d2.naver.com/helloworld/6560422

### KafkaProducer

send()를 호출함으로써 Record를 전송한다.

### RecordAccumulator

사용자가 KafkaProducer의 send()를 호출하면 Record가 바로 Broker로 전송되는 것이 아니라 RecordAccumulator에 저장된다. 
그리고 실제로 Broker에 전송되는 것은 이후에 비동기적으로 이루어진다.

### Sender

KafkaProducer는 별도의 Sender Thread를 생성한다. Sender Thread는 RecordAccumulator에 저장된 Record들을 Broker로 전송하는 역할을 한다. 

그리고 Broker의 응답을 받아서 사용자가 Record 전송 시 설정한 콜백이 있으면 실행하고, Broker로부터 받은 응답 결과를 Future를 통해서 사용자에게 전달한다.

### Serialization

사용자로부터 전달된 Record의 Key, Value는 지정된 Serializer에 의해서 Byte Array로 변환된다. 
Serializer는 key.serializer, value.serializer 설정값으로 지정하거나, KafkaProducer 생성 시 지정할 수 있다.

### Partitioning

Kafka의 Topic은 여러 개의 Partition으로 나뉘어 있는데, 사용자의 Record는 지정된 Partitioner에 의해서 어떤 파티션으로 보내질지 정해진다. Partitioner는 기본적으로 Record를 받아서 Partition Number를 반환하는 역할을 한다. partitioner.class를 설정하여 Partitioner를 지정할 수 있으며, Partitioner를 지정하지 않으면 org.apache.kafka.clients.producer.internals.DefaultPartitioner가 사용된다.

Record 생성 시 Partition 지정이 가능하기 때문에, Partition이 지정되어 있는 경우에는 Partitioner를 사용하지 않고 지정된 Partition이 사용된다. Record에 지정된 Partition이 없는 경우 DefaultPartitioner는 다음과 같이 동작한다.

- Key 값이 있는 경우 Key 값의 Hash 값을 이용해서 Partition을 할당한다.
- Key 값이 없는 경우 Round-Robin 방식으로 Partition이 할당된다.

### Compression

사용자가 전송하려는 Record는 압축을 함으로써 네트워크 전송 비용도 줄일 수 있고 저장 비용도 줄일 수 있다. 
Record는 RecordAccumulator에 저장될 때 바로 압축되어 저장된다. compression.type을 설정하여 압축 시 사용할 코덱을 지정할 수 있다. 다음과 같은 코덱를 사용할 수 있으며 지정하지 않는 경우 기본값은 none이다.

- gzip
- snappy
- lz4

### RecordAccumulator append()

사용자가 전송하려는 Record는 전송 전에 먼저 RecordAccumulator에 저장된다. 
RecordAccumulator는 batches라는 Map을 가지고 있는데, 이 Map의 Key는 TopicPartition이고, Value는 Deque<RecordBatch>이다.

~~~java
private final ConcurrentMap<TopicPartition, Deque<ProducerBatch>> batches;
~~~

RecordAccumulator에 저장하기 전에 Record의 Serialized Size를 검사한다. 
Serialized Size가 max.request.size 설정값 또는 buffer.memory 설정값보다 크면 RecordTooLargeException이 발생한다. 
크기가 문제 없으면, RecordAccumulator의 append()를 이용해서 저장한다.

## KafkaConsumer Client Internals

https://d2.naver.com/helloworld/0974525


## Issue
https://saramin.github.io/2019-09-17-kafka/

### Rebalancing
https://joooootopia.tistory.com/30


## References

- Kafka : https://youtu.be/waw0XXNX-uQ?list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j
- Topic : https://youtu.be/7QfEpRTRdIQ
- Producer : https://www.youtube.com/watch?v=aAu0FE3nvbk
- Consumer : https://youtu.be/rBVCvv9skT4?list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j
- Consumer lag : https://youtu.be/D7C_CFjrzBk

- Kafka 이해하기 : https://medium.com/@umanking/%EC%B9%B4%ED%94%84%EC%B9%B4%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EC%9D%B4%EC%95%BC%EA%B8%B0-%ED%95%98%EA%B8%B0%EC%A0%84%EC%97%90-%EB%A8%BC%EC%A0%80-data%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EC%9D%B4%EC%95%BC%EA%B8%B0%ED%95%B4%EB%B3%B4%EC%9E%90-d2e3ca2f3c2
