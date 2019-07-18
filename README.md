# AvroMirrorMaker
AvroMirrorMaker allows you to replicate data from topics which use different schema registry provider.

## Requirments
 - Java 8
 - Scala 2.12

## Build
`gradle jar`

## Setup
Put `avro-mirror-maker-VERSION-all.jar` from `build/libs` to shared directory for kafka libs or add path to this jar:

```bash
export CLASSPATH=$CLASSPATH:<path to jar>
```

## Usage
```bash
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler ConfluentToHortonHandle --message.handler.args http://confluent:port;http://horton:port
./kafka-mirror-maker --consumer.config consumer.properties --producer.config producer.properties --message.handler HortonToConfluentHandler --message.handler.args http://horton:port;http://confluent:port
```