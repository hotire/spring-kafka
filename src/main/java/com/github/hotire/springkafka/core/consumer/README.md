# Consumer 


## Poll

KafkaConsumer는 사용자가 직접 사용하는 클래스로, 사용자는 KafkaConsumer의 poll 메서드를 사용해 브로커에서 데이터를 가져온다. 

만약 컨슈머 그룹 내에 특정 컨슈머의 처리가 일정 시간(max.poll.interval.ms 설정만큼) 정지된다면 해당 컨슈머는 그룹에서 제외되고 나머지 컨슈머들로만 데이터가 분배된다. (리밸런싱)

### Join Group

- 첫 번째 단계에서는 JoinGroup 요청을 GroupCoordinator로 보내 그룹에 참여한다. 
이후 리더(leader)로 선정된 컨슈머는 그룹 내 파티션을 할당한다. 모든 컨슈머는 Synchronization barrier를 넘어가기 전에 메시지 처리를 중지하고 오프셋을 커밋해야 한다.

- 두 번째 단계에서 모든 컨슈머는 SyncGroup 요청을 보낸다. 
리더는 SyncGroup 요청을 보낼 때 파티션 할당 결과를 요청에 포함시킨다. GroupCoordinator는 파티션 할당 결과를 SyncGroup의 응답으로 준다. 오프셋 초기화 과정을 끝낸 후 컨슈머는 브로커에서 데이터를 가져올 수 있다.



### Consumer Group 

같은 group.id를 사용하는 컨슈머를 묶어서 컨슈머 그룹이라고 한다. 

만약 컨슈머 그룹의 처리량을 늘리고 싶다면 group.id가 같은 새로운 KafkaConsumer를 만들어서 poll 메서드를 호출하면 된다. 같은 컨슈머 그룹에 속한 KafkaConsumer는 다른 파티션을 할당받기 때문에 컨슈머 그룹 내 데이터 처리를 확장한다. 

Kafka는 파티션 단위로 데이터를 분배하기 때문에 파티션의 수보다 많은 컨슈머를 그룹에 추가한 경우 파티션의 수를 초과한 컨슈머는 파티션을 할당받지 못하여 데이터를 소비하지 못한다.

- 브로커 중 하나가 컨슈머 그룹를 관리하고 이를 GroupCoordinator라고 부른다. GroupCoordinator는 그룹의 메타데이터와 그룹을 관리한다.

### Kafka Consumer Error Handling, Retry, and Recovery

- https://blogs.perficient.com/2021/02/15/kafka-consumer-error-handling-retry-and-recovery/

## Rebalance

컨슈머 그룹에 새로운 컨슈머가 추가되거나 컨슈머 그룹에 속해 있던 컨슈머가 제외되는 경우에 그룹 내 파티션을 다시 할당해야 하므로 리밸런스가 발생한다.

컨슈머 리밸런스가 일어날 때 모든 컨슈머에 할당된 파티션이 해제(revoke)되므로 새로 파티션이 할당되기 전까지 데이터 처리가 일시 정지된다.

### Kafka consumer의 Automatic Commit은 중복

Auto commit에서 중복발생

- enable.auto.commit = true
- auto.commit.interval.ms = 5000ms

상기와 같이 설정했을 경우 5초마다 poll이 호출되면 확인하여 commit이 수행된다.

 

아래와 같은 경우가 생길 수 있다.

1) poll()호출을 통해 record 100개 가져옴(→ 이때 offset commit)

2) record 100개 중 30개 처리 완료(ex. 데이터 저장완료)

3) 갑자기! 어떤 이유(topic partition개수 증가 혹은 consumer 개수 증감)로 rebalancing 시작

4) consumer들이 re-assign됨

5) consumer는 1)에서 commit된 offset부터 다시 데이터를 polling

6) 다시가져온 record를 처리 수행(중복발생)

- https://blog.voidmainvoid.net/262



### AckMode

- MANUAL - 마지막 폴의 모든 결과가 처리되었을 때 ack가 큐에 대기되고 오프셋이 한 작업으로 커밋됩니다.
- MANUAL_IMMEDIATE - 리스너 스레드에서 ack가 수행되는 한 오프셋이 즉시 커밋됩니다(동기화 또는 비동기화).
- https://docs.spring.io/spring-kafka/reference/html/#committing-offsets


### References

- KafkaConsumer Client Internals : https://d2.naver.com/helloworld/0974525