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
topic를 나누는 단위 

### Offset
파티션 내에서 각 메시자가 가지는 unique id

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

### KafkaConsumer Client Internals

https://d2.naver.com/helloworld/0974525


## 영상들 

- Kafka : https://youtu.be/waw0XXNX-uQ?list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j
- Topic : https://youtu.be/7QfEpRTRdIQ
- Producer : https://www.youtube.com/watch?v=aAu0FE3nvbk
- Consumer : https://youtu.be/rBVCvv9skT4?list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j
- Consumer lag : https://youtu.be/D7C_CFjrzBk
