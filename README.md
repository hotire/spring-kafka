# spring-kafka
study 

![kafka](/doc/img/kafka.png)

### Getting Started 

- https://codenotfound.com/spring-kafka-consumer-producer-example.html

- https://blog.naver.com/gngh0101/221763474986

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
메시지 종류 

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

