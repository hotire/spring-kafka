# Partition

일반적으로 많은 파티션은 높은 처리량을 이끈다.

파티션이 늘어나면 컨슈머의 동시성이 올라가고 처리량이 올라가게된다. 

하지만 파티션이 늘어난다고 해서 꼭 처리량이 올라가는 것은 아니다.

브로커당 2천개 정도의 최대 파티션 수를 권장한다.

### 파티션 수 

프로듀서, 컨슈머의 한 파티션이 요구하는 처리량을 기준으로 결정한다.

프로듀서의 처리량에 영향을 끼치는 요소는 배치 사이즈, 압축코덱, Acks 타입, 복제 수 등이 있다. 

컨슈머는 데이터 처리하는 로직에 따라 처리량이 바뀐다.


컨슈머의 한 파티션 처리량 C

목표 처리량 T

T / C 의 partition이 필요하다. 

프로듀서의 한 파티션 처리량 P

목표 처리량 T 

T / P 의  partition이 필요하다. 


MAX (T / P, T / C)로 나오게 된다.


### References 

- https://www.confluent.io/blog/how-choose-number-topics-partitions-kafka-cluster/
- https://devidea.tistory.com/95