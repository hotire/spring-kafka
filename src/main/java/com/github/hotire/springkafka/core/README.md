# Kafka Produce 


## KafkaProducer

### doSend

~~~java
  private Future<RecordMetadata> doSend(ProducerRecord<K, V> record, Callback callback) {
       .....
       .....
               try {
                    serializedKey = keySerializer.serialize(record.topic(), record.headers(), record.key());
                } catch (ClassCastException cce) {
                    throw new SerializationException("Can't convert key of class " + record.key().getClass().getName() +
                            " to class " + producerConfig.getClass(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG).getName() +
                            " specified in key.serializer", cce);
                }
                byte[] serializedValue;
                try {
                    serializedValue = valueSerializer.serialize(record.topic(), record.headers(), record.value());
                } catch (ClassCastException cce) {
                    throw new SerializationException("Can't convert value of class " + record.value().getClass().getName() +
                            " to class " + producerConfig.getClass(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG).getName() +
                            " specified in value.serializer", cce);
                }
      .....
      .....
    int serializedSize = AbstractRecords.estimateSizeInBytesUpperBound(apiVersions.maxUsableProduceMagic(),
                    compressionType, serializedKey, serializedValue, headers);
            ensureValidRecordSize(serializedSize);
  }
~~~

RecordAccumulator에 저장하기 전에 Record의 Serialized Size를 검사한다. 
Serialized Size가 max.request.size 설정값 또는 buffer.memory 설정값보다 크면 RecordTooLargeException이 발생한다. 
크기가 문제 없으면, RecordAccumulator의 append()를 이용해서 저장한다.

 

