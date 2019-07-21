package avro.mirrormaker;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.HashMap;
import java.util.Map;

public class ConfluentToConfluentSerializer implements AvroSerializer {
    private KafkaAvroDeserializer confluentDeserializer;
    private KafkaAvroSerializer confluentSerializer;

    public ConfluentToConfluentSerializer() {
        confluentDeserializer = new KafkaAvroDeserializer();
        confluentSerializer = new KafkaAvroSerializer();
    }

    public void configure(String sourceConfluentUrl, String targetConfluentUrl) {
        Map<String, Object> firstConfluentConfig = new HashMap<>();
        Map<String, Object> secondConfluentConfig = new HashMap<>();

        firstConfluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, sourceConfluentUrl);
        secondConfluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, targetConfluentUrl);

        confluentDeserializer.configure(firstConfluentConfig, false);
        confluentSerializer.configure(secondConfluentConfig, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> firstConfluentConfig = new HashMap<>(configs);
        Map<String, Object> secondConfluentConfig = new HashMap<>(configs);

        firstConfluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, configs.get(Common.SOURCE_SCHEMA_REGISTRY_URL_CONFIG));
        secondConfluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, configs.get(Common.TARGET_SCHEMA_REGISTRY_URL_CONFIG));

        confluentDeserializer.configure(firstConfluentConfig, false);
        confluentSerializer.configure(secondConfluentConfig, false);
    }

    @Override
    public byte[] serialize(String topic, byte[] data) {
        return confluentSerializer.serialize(topic, confluentDeserializer.deserialize(topic, data));
    }

    @Override
    public void close() {
        confluentDeserializer.close();
        confluentSerializer.close();
    }
}
