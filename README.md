# AvroMirrorMaker
AvroMirrorMaker allows you to replicate data from topics which use different schema registry provider.

```text
Confluent schema registry -> Confluent schema registry
Horton schema registry -> Horton schema registry
Confluent schema registry -> Horton schema registry
Horton schema registry -> Confluent schema registry
```

## Requirments
 - Java 8

## Build
`gradle jar`

## Setup
Put `avro-mirror-maker-VERSION-all.jar` from `build/libs` to shared directory for kafka libs or add path to this jar:

```bash
export CLASSPATH=$CLASSPATH:<path to jar>
```

## Usage
```bash
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler avro.mirrormaker.ConfluentToHortonHandler --message.handler.args http://confluent:port,http://horton:port
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler avro.mirrormaker.HortonToConfluentHandler --message.handler.args http://horton:port,http://confluent:port
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler avro.mirrormaker.ConfluentToConfluentHandler --message.handler.args http://confluent1:port,http://confluent2:port
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler avro.mirrormaker.HortonToHortonHandler --message.handler.args http://horton1:port,http://horton2:port
```

## Tested versions
|AvroMirrorMaker       | Kafka   | 
|----------------|--------|
| 0.1 | 2.0.0 (Confluent 5.0.0)   |
| 0.2 | 2.1.0 (Confluent 5.1.0)   |