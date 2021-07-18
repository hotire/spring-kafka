# Consumer 

### Kafka Consumer Error Handling, Retry, and Recovery

- https://blogs.perficient.com/2021/02/15/kafka-consumer-error-handling-retry-and-recovery/


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