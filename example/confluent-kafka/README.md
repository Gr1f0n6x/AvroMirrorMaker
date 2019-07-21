# Local Kafka Clusters example
## Run
1.
```bash
docker-compose up
```
2. Create topics:
```bash
# on first cluster:
docker exec -it kafka-1 /bin/bash
kafka-topics --create --topic avro_confluent --zookeeper zookeeper-1:2181 --partitions 1 --replication-factor 1
# on second cluster:
docker exec -it kafka-2 /bin/bash 
kafka-topics --create --topic avro_horton --zookeeper zookeeper-2:2182 --partitions 1 --replication-factor 1


```
3. Start mirror-maker on first cluster:
```bash
# If you use custom broker.properties make sure you set auto.create.topics.enable=true otherwise create replicated topics manually
docker exec -it kafka-1 /bin/bash
kafka-mirror-maker --consumer.config /confluent-consumer.properties --producer.config /horton-producer.properties --whitelist avro_confluent \
 --message.handler avro.mirrormaker.ConfluentToHortonHandler --message.handler.args http://confluent-registry:8081,http://horton-registry:9090/api/v1
docker exec -it kafka-2 /bin/bash
kafka-mirror-maker --consumer.config /horton-consumer.properties --producer.config /confluent-producer.properties --whitelist avro_horton \
 --message.handler avro.mirrormaker.HortonToConfluentHandler --message.handler.args http://horton-registry:9090/api/v1,http://confluent-registry:8081
```

## Known issues
https://github.com/grzegorz8/hortonworks-avro-converter/blob/master/README.md#known-issues

