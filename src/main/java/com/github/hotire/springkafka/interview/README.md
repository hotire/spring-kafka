# Kafka

Kafka는 Distributed Streaming Platform 분산 스트리밍 플랫폼 (Streaming : 데이터가 지속적으로 유입되고 나가는 과정에서 분석이나 질의)

성능이 뛰어나고 원하는 기간만큼 안정적으로 데이터를 저장한다.  또한 모든 기능이 분산되어 있어 확장성과 내결함성(fault tolerance)이 뛰어나다. 

이러한 특징 덕분에 Kafka는 기존 메시징 시스템(Active MQ, Rabbit MQ)을 대체하기도 하고 시스템 로그를 모으거나 데이터 가공을 위해 파이프라인을 구성할 때 등 다양한 경우에 사용된다. 

Kafka는 TCP 위에서 동작하는 자체 바이너리 프로토콜을 사용하고, 모든 바이너리 프로토콜은 요청과 응답의 쌍으로 구성된다. 

바이너리 프로토콜을 적절히 구현한 프로듀서(KafkaProducer)와 컨슈머(KafkaConsumer)를 클라이언트로 제공하며 

KafkaProducer를 사용하여 데이터를 발행(publish)하고 KafkaConsumer를 사용하여 데이터를 구독(subscribe)한다. 

### Broker

하나의 카프카 서버를 브로커라고 한다. 

브로커는 프로듀서로부터 메시지를 수신하고 오프셋을 지정한 후 해당 메시지를 디스크에 저장한다. 또한 컨슈머의 파티션 읽기 요청에 응답하고 디스크에 수록된 메시지를 전송한다. 

### 클러스터 

브로커는 클러스터의 일부로 동작하도록 설계되었다. 즉 여러 개의 브로커가 하나의 클러스터에 포함될 수 있다. 

클러스터의 여러 브로커 중 하나는 자동으로 컨트롤러의 기능을 수행한다. 

### 컨트롤러

브로커에게 담당 파티션을 할당하고 모니터링한다. 

### 리더 (파티션 리더)

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


## Consumer  

https://d2.naver.com/helloworld/0974525

### Consumer Group

같은 group.id를 사용하는 컨슈머를 묶어서 컨슈머 그룹이라고 한다. 레코드(record)는 컨슈머 그룹 내에 오직 1개의 컨슈머로만 전달된다.

### Consumer Group Leader 

### KafkaConsumer

KafkaConsumer는 사용자가 직접 사용하는 클래스로, 사용자는 KafkaConsumer의 poll 메서드를 사용해 브로커에서 데이터를 가져올 수 있다.

- poll : 컨슈머 그룹에 참여한 후 브로커로부터 데이터를 가져온다.  (subscribe : group.id 전달하면 구독한다.)

내부 구성 요소로 ConsumerNetworkClient, SubscriptionState, ConsumerCoordinator, Fetcher, HeartBeat로 구성된다. 

### ConsumerNetworkClient

ConsumerNetworkClient는 KafkaConsumer의 모든 네트워크 통신을 담당하는 클래스로 비동기로 처리하고 

RequestFuture로 결과를 반환한다. 

- Unsent Map 에 먼저 저장한다. 
- RequestFutureCompletionHandler로 success / fail 시에 pendingCompletion 들어갈 수 있도록 한다. 
- 매번 poll 호출시 pendingCompletion 체크해서 전부 처리한다. 
- 매번 poll 호출시 Unsent 전송한다 . 

### SubscriptionState

KafkaConsumer는 다른 메시지 시스템과 달리 자신이 소비하는 토픽, 파티션, 오프셋 정보를 추적 및 관리한다. 

SubscriptionState가 토픽, 파티션, 오프셋 정보 관리를 담당하고 있다.

토픽, 파티션 할당은 assign 메서드를 통해 이루어진다. 컨슈머의 그룹 관리 기능을 사용하지 않고 사용자가 assign 메서드를 직접 호출하여 수동으로 토픽, 파티션을 할당할 수 있는데 이 경우에는 컨슈머 리밸런스가 일어나지 않는다.

assign 메서드를 통해 할당된 파티션은 초기 오프셋 값 설정이 필요하다. 

초기 오프셋 값이 없으면 Fetch가 불가능한 파티션으로 분류된다. 

seek 메서드를 통해 초기 오프셋 값을 설정한다. 초기 오프셋 설정은 오프셋 초기화 과정을 통해 이루어진다. 사용자가 KafkaConsumer의 seek 메서드를 사용하여 설정할 수도 있다.


반면 컨슈머의 그룹 관리 기능을 사용하기 위해서는 특정 토픽에 대해 구독 요청을 해야 한다. 

구독 요청은 KafkaConsumer의 subscribe 메서드를 통해 한다. 

사용자가 구독을 요청한 토픽 정보는 SubscriptionState의 subscription에 저장된다. 

subscription에 저장된 토픽 정보는 컨슈머 리밸런스 과정에서 사용된다. 그룹 관리 기능을 사용한 경우에는 컨슈머 리밸런스 과정에서 코디네이터에 의해 토픽, 파티션이 할당된다.

### ConsumerCoordinator

ConsumerCoordinator는 컨슈머 리밸런스, 오프셋 초기화(일부), 오프셋 커밋을 담당한다.

- 컨슈머 리밸런스 : JoinGroupResponseHandler, SyncGroupResponseHandler

- 오프셋 초기화 : OffsetFetchResponseHandler

- 오프셋 커밋 : OffsetCommitResponseHandler

- Heartbeat : HeartbeatResponseHandler



### GroupCoordinator

브로커 중 하나가 컨슈머 그룹를 관리하고 이를 GroupCoordinator라고 부른다. GroupCoordinator는 그룹의 메타데이터와 그룹을 관리한다.

### 리밸런스(rebalance)

Kafka는 리밸런스(rebalance)를 통해 컨슈머의 할당된 파티션을 다른 컨슈머로 이동시킨다. 

컨슈머 그룹에 새로운 컨슈머가 추가되거나 컨슈머 그룹에 속해 있던 컨슈머가 제외되는 경우에 그룹 내 파티션을 다시 할당해야 하므로 리밸런스가 발생한다.

컨슈머 리밸런스가 일어날 때 모든 컨슈머에 할당된 파티션이 해제(revoke)되므로 새로 파티션이 할당되기 전까지 데이터 처리가 일시 정지된다.

컨슈머 리밸런스 프로토콜은 2단계로 이루어져 있다.

1. 첫 번째 단계에서는 JoinGroup 요청을 GroupCoordinator로 보내 그룹에 참여한다. 이후 리더(leader)로 선정된 컨슈머는 그룹 내 파티션을 할당한다. 모든 컨슈머는 Synchronization barrier를 넘어가기 전에 메시지 처리를 중지하고 오프셋을 커밋해야 한다.

2. 두 번째 단계에서 모든 컨슈머는 SyncGroup 요청을 보낸다. 리더는 SyncGroup 요청을 보낼 때 파티션 할당 결과를 요청에 포함시킨다. GroupCoordinator는 파티션 할당 결과를 SyncGroup의 응답으로 준다

- 리밸런스 원인 
    1. 컨슈머 그룹에 새로운 컨슈머가 추가되거나 컨슈머 그룹에 속해 있던 컨슈머가 제외되는 경우
    2. 만약 컨슈머 그룹 내에 특정 컨슈머의 처리가 일정 시간(max.poll.interval.ms 설정만큼) 정지할 경우 제외

### GroupCoordinator 찾기

GroupCoordinator는 그룹이 구독한 토픽과 파티션을 관리하고 그룹의 멤버를 관리한다. 따라서 KafkaConsumer가 그룹 참여를 요청하기 위해서는 먼저 GroupCoordinator를 찾아야 한다.

FindCoordinator API를 통해 찾을 수 있다. https://kafka.apache.org/protocol#The_Messages_FindCoordinator

1. Join : KafkaConsumer가 GroupCoordinator에게 그룹 참여를 요청하는 단계이다. GroupCoordinator를 찾은 ConsumerCoordinator는 JoinGroup API를 사용하여 GroupCoordinator에게 그룹 참여를 요청한다.
JoinGroup API 요청을 보내기 전에 Heartbeat 스레드가 JoinGroup을 방해하지 못하도록 Heartbeat 스레드를 일시 정지시킨다.
JoinGroup API 요청에는 groupId, sessionTimeout, rebalanceTimeout, groupProtocols이 포함된다.
GroupCoordinator는 응답으로 현재 컨슈머의 Id(memberid)와 그룹 리더의 Id(leaderid), 그룹 멤버 정보(members), 그룹 파티션 할당 정책(group_protocol) 보낸다.  
memberid와 leaderid가 같은 컨슈머가 리더가 되며, 리더는 그룹 내에 파티션을 할당할 책임이 있다.   
    - groupId: 컨슈머가 속할 그룹을 나타낸다.
    - sessionTimeout: 컨슈머가 sessionTimeout 시간 내에 heartbeat 요청을 GroupCoordinator에 보내지 않으면 GroupCoordinator는 해당 컨슈머가 죽은 것으로 판단한다
    - rebalanceTimeout: 그룹에 속한 컨슈머들은 리밸런스가 발생했을 때 rebalanceTimeout 이내에 JoinGroup 요청을 보내야 한다. rebalanceTimeout 이내에 JoinGroup 요청을 보내지 않은 컨슈머는 컨슈머 그룹에서 제외된다.
    - groupProtocols: 메타데이터로 컨슈머가 구독하려는 토픽과 컨슈머가 지원하는 파티션 할당 정책이 포함된다. (기본값은 RangeAssignor) 
    - partition.assignment.strategy : RangeAssignor, RoundRobinAssignor, StickyAssignor 외에도 custom이 가능 
        - RangeAssignor : 파티션은 숫자 순서대로 정렬을 하고 컨슈머는 사전 순서 정렬이후, 토픽의 파티션을 컨슈머 숫자로 나누어 컨슈머에게 할당해야 하는 파티션 수를 결정한다. 나누어지지 않으면 앞쪽 컨슈머가 더 할당 가져간다. 
        - RoundRobinAssignor : 모든 파티션을 컨슈머에게 번갈아가면서 할당한다.
        - StickyAssignor : 최대한 파티션을 균등하게 분배하고, 파티션 재할당이 이루어질 때 파티션의 이동을 최소화하려는 할당 정책이다. 
2. Sync : 그룹에 참여하는 모든 컨슈머는 SyncGroup API 요청을 GroupCoordinator에 보내고 리더는 파티션 할당 결과를 SyncGroup API 요청에 포함시킨다.
GroupCoordinator는 SyncGroup API 응답으로 컨슈머에 할당된 토픽, 파티션 정보를 보낸다
    - SyncGroup API 응답을 받은 컨슈머는 자신에게 할당된 토픽, 파티션 정보를 SubscriptionState의 assign 메서드를 사용하여 업데이트한다.
    - 최신 버전에서는 컨슈머 리밸런스 과정에서 KafkaConsumer 처리가 정지되는 Stop the world 현상을 없애기 위해 컨슈머 리밸런스 과정을 증분으로 진행하는 기능이 추가되었다.


### 오프셋 초기화

브로커에서 데이터를 읽기 위해서는 파티션의 초기 오프셋 값이 필요하다. SubscriptionState의 assign 메서드를 통해 할당된 파티션은 초기 오프셋 값이 없다. KafkaConsumer는 오프셋 초기화 과정을 통해 초기 오프셋 값을 설정한다.    
    
커밋된 오프셋을 가져오는 과정과 커밋된 오프셋이 없는 경우 오프셋 초기화 정책에 따라 오프셋을 초기화하기 위해 파티션의 오프셋을 가져오는 과정으로 이루어진다.

### 커밋된 오프셋 가져오기

초기 오프셋 값이 없는 경우 KafkaConsumer는 ConsumerCoordinator를 통해 커밋된 오프셋 값을 확인한다. 
ConsumerCoordinator는 OffsetFetch API를 통해 GroupCoordinator에게 커밋된 오프셋 정보를 요청하고 응답 받으면 SubscriptionState 업데이트한다.
이후 SubscriptionState 오프셋 값은  Fetcher에 의해 파티션의 오프셋 초기값으로 설정된다

### 파티션의 오프셋 가져오기

만약 커밋된 오프셋 정보가 없다면 KafkaConsumer는 auto.offset.reset 설정에 따라 오프셋을 초기화한다. auto.offset.reset에는 earliest, latest, none을 설정할 수 있다. 

- earliest: 파티션의 가장 처음 오프셋을 사용한다.
- latest: 파티션의 가장 마지막 오프셋을 사용한다. (기본 값)
- none: 오프셋을 초기화하지 않는다. 

Fetcher는 파티션의 가장 처음 오프셋과 가장 마지막 오프셋을 알아내기 위해 특정 시간(timestamp)에 해당하는 오프셋을 조회하는 ListOffsets API를 활용하여 파티션 리더 브로커로 ListOffsets API 요청에 timestamp를 -2로 설정하면 가장 처음 오프셋을 알 수 있고 timestamp를 -1로 설정하면 가장 마지막 오프셋을 알 수 있다. auto.offset.reset가 earliest인 경우에는 ListOffsets API 요청에 timestamp를 -2로 설정하고 latest인 경우에는 timestamp를 -1로 설정한다.
응답받은 오프셋 값은 SubscriptionState의 seek 메서드를 통해 파티션의 초기 오프셋으로 설정된다(그림 11의 14).
 
 
### 오프셋 커밋

컨슈머가 오프셋 정보를 관리하기 때문에 데이터를 읽은 후 컨슈머는 적절한 시점에 오프셋을 커밋해야 한다.

- enable.auto.commit 설정이 true인 경우 KafkaConsumer가 auto.commit.interval.ms마다 오프셋을 자동으로 커밋한다. enable.auto.commit의 기본값은 true이고 auto.commit.interval.ms의 기본값은 5000ms(5초)이다.
- 수동 커밋(commitSync) : commitSync 메서드를 사용하여 오프셋 커밋을 요청하면 KafkaConsumer가 오프셋 커밋 요청이 끝날 때까지 대기하기 때문에 KafkaConsumer가 일시 중지된다. 만약 이를 방지하고 싶다면 commitAsync 메서드를 사용하여 비동기 커밋을 해야 한다. GroupCoordinator는 OffsetCommit API의 응답으로 오류 코드(error_code)를 보내는데 오류 코드가 0이면 정상이다.
 
### HeartBeat

0.10.1 이전에는 HeartBeat 시간(Consumer가 중단되진 않았는지 GroupCoordinator가 감시하는 시간)과 Polling 간격 시간(브로커로부터 가져온 데이터를 처리하는 시간)이 구분되지 않아서 싱글 스레드로 KafkaConsumer을 사용할 때 Polling 간격이 session timout을 초과하면 컨슈머 그룹에서 제외되는 문제가 있었다. 또한 데이터 처리 시간이 항상 session timout보다 긴 경우에는 사용자가 문제를 인지하고 수정하기 전까지는 프로세스가 진행되지 않는 문제가 있었다. 
    
- max.poll.interval.ms : poll 메서드는 max.poll.interval.ms 이내에 호출되어야 한다. 호출되지 않으면 컨슈머 그룹에서 제외하고 리밸런스가 일어난다. (기본값은 300000ms(5분))
- heartbeat.interval.ms : Heartbeat 전송 시간 간격이다. HeartBeat 스레드는 heartbeat.interval.ms 간격으로 Heartbeat을 전송한다. heartbeat.interval.ms의 값은 항상 session.timeout.ms보다 작아야 하며 일반적으로 session.timeout.ms의 1/3 이하로 설정한다. ( 3000ms(3초)
- session.timeout.ms : session.timeout.ms 내에 HeartBeat이 도착하지 않으면 브로커는 해당 컨슈머를 그룹에서 제거한다. (10000ms(10초)이다.)

Process 스레드가 정상적으로 동작하지 않는다면 max.poll.interval.ms으로 감지가 된다. 만약 KafkaConsumer가 정상이 아닌 경우에는 session.timeout.ms로 감지된다.


### Fetcher

Consumer 리밸런스와 오프셋 초기화 과정이 끝나면 KafkaConsumer의 poll 메서드를 통해 브로커로부터 데이터를 가져오는 역할을 한다. 

KafkaConsumer의 poll 메서드가 호출되면 먼저 Fetcher의 fetchedRecords 메서드가 호출된다. 


- fetchedRecords : 내부 캐시인 nextInLineRecords와 completedFetches를 확인하여 브로커로부터 이미 가져온 데이터가 있는 경우에는 max.poll.records 설정 값만큼 레코드를 반환한다. max.poll.records의 기본값은 500이다.
- sendFetches :  파티션 리더가 위치한 각 브로커에게 보낸다. KafkaConsumer는 Fetcher가 브로커로부터 응답을 받을 때까지 대기한다.
    - fetch.max.wait.ms : 브로커가 Fetch API 요청을 받았을 때 fetch.min.bytes 값만큼 데이터가 없는 경우 응답을 주기까지 최대로 기다릴 시간이다. (기본 값 500ms(0.5초))
    - fetch.min.bytes : Fetch API 요청이 왔을 때 브로커는 최소한 fetch.min.bytes 값만큼 데이터를 반환한다. 데이터가 충분하지 못하면 데이터가 누적되길 기다린다. 기본 값은 1
    - fetch.max.bytes : Fetch API 요청에 대해 브로커가 반환해야 하는 최대 데이터 크기로 절대적으로 적용되지 않고 첫 번째 파티션의 첫 번째 메시지가 이 값보다 크다면 컨슈머가 계속 진행될 수 있도록 데이터가 반환된다. (기본값은 52428800(50MiB)이다.)
    - max.partition.fetch.bytes : 브로커가 반환할 파티션당 최대 데이터 크기로, fetch.max.bytes 와 마찬가지로 이상이라도 반환한다. 

Fetcher가 브로커로부터 응답을 받으면 KafkaConsumer는 Fetcher의 fetchedRecords 메서드를 다시 호출하여 사용자에게 반환할 레코드를 가져온다. 

KafkaConsumer는 레코드를 사용자에게 반환하기 전에 다음 poll 메서드 호출 시에 브로커로부터 응답을 대기하는 시간을 없애기 위해 Fetcher의 sendFetches 메서드를 호출한 후 레코드를 반환한다.    


    
## NetworkClient

https://d2.naver.com/helloworld/0853669

Kafka 클라이언트인 KafkaProducer와 KafkaConsumer는 브로커 노드와 통신하기 위한 클래스.


### ClusterConnectionStates

NetworkClient는 브로커와의 연결 상태를 ClusterConnectionStates로 관리한다.

### NodeConnectionState

현재 연결 상태를 나타내는 ConnectionState와 마지막으로 연결을 시도했던 시간 정보가 기록된다.

### ConnectionState

- DISCONNECTED: 브로커와 연결이 끊긴 상태
- CONNECTING : 소켓을 생성하고 연결을 생성 중인 상태
- CHECKING_API_VERSIONS	: 연결이 생성되었고 브로커와 API 버전이 호환되는지 확인 중인 상태
- READY	: 브로커로 요청을 전송할 수 있는 상태

CONNECTING -> CHECKING_API_VERSIONS -> READY

만약 각 연결 단계에서 문제가 발생한다면 DISCONNECTED 상태로 바뀌고 브로커와 통신하기 위해 다시 연결을 시도한다.

### DISCONNECTED 상태

Kafka 클라이언트와 브로커 노드의 연결이 끊긴 상태로 브로커에 요청을 보내기 위해서 다시 연결을 시도해야한다. 

너무 빈번하게 재시도를 하지 않도록 reconnect.backoff.ms(default 50) 이후에 연결을 시도한다.  

- 브로커 노드로의 연결 초기화가 실패한 경우
- API 버전이 호환되지 않는 경우
- 브로커 노드로 요청 전송이 실패한 경우
- 요청이 전송되고 응답을 기다리다가 타임아웃이 발생한 경우
- 일정 시간 동안 브로커로 새로운 요청을 보내지 않은 경우

### CONNECTING 상태

로커와 연결을 시도할 때 CONNECTING 상태로 설정한다. 

브로커와 통신하기 위해 

send.buffer.bytes에 설정된 송신 버퍼(send buffer size)의 크기와 receive.buffer.bytes에 설정된 수신 버퍼(receive buffer)의 크기

SocketChannel을 생성한다. 크기를 별도로 설정하지 않으면 송신 버퍼의 크기는 128KB이고, 수신 버퍼의 크기는 64KB이다. 

만약 값을 '-1'로 설정하면 실행하는 운영체제의 기본값인 SO_SNDBUF와 SO_RCVBUF가 적용된다. (커널의 수신, 송신 버퍼)

### CHECKINGAPIVERSIONS 상태, READY 상태

Kafka 클라이언트는 자신의 API 버전 정보를 담은 ApiVersionRequest를 생성해서 브로커로 전송한다. 

그러면 브로커가 호환되는 버전인지를 판단해서 ApiVersionResponse를 Kafka 클라이언트에 돌려준다. Kafka 클라이언트는 이 응답을 통해 API 호환 여부를 알 수 있다.

API가 문제없이 호환된다면 브로커의 연결 상태는 READY 상태가 된다.


### IdleExpiryManager

불필요한 연결을 정리하기 위해 IdleExpiryManager를 사용한다. READY 상태로 통신할 준비가 되어 있는 브로커 연결을 일정 시간 동안 사용하지 않으면 IdleExpiryManager에 의해 연결이 정리될 수 있다.

### Request 과정 

- sender
    - sender.runOnce -> sendProducerData(client.send) / client.poll
- client
    1. send
    2. doSend
    3. InFlightRequest 생성하고 selector로 전달 
    4. poll
    5. handleCompletedReceives


    