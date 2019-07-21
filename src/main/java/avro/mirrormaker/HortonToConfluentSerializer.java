package avro.mirrormaker;

import com.hortonworks.registries.schemaregistry.client.SchemaRegistryClient;
import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.HashMap;
import java.util.Map;

public class HortonToConfluentSerializer implements AvroSerializer {
    private KafkaAvroDeserializer hortonDeserializer;
    private KafkaAvroSerializer confluentSerializer;

    public HortonToConfluentSerializer() {
        hortonDeserializer = new KafkaAvroDeserializer();
        confluentSerializer = new KafkaAvroSerializer();
    }

    public void configure(String hortonUrl, String confluentUrl) {
        Map<String, Object> hortonConfig = new HashMap<>();
        Map<String, Object> confluentConfig = new HashMap<>();

        hortonConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), hortonUrl);
        confluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, confluentUrl);

        hortonDeserializer.configure(hortonConfig, false);
        confluentSerializer.configure(confluentConfig, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> hortonConfig = new HashMap<>(configs);
        Map<String, Object> confluentConfig = new HashMap<>(configs);

        hortonConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), configs.get(Common.SOURCE_SCHEMA_REGISTRY_URL_CONFIG));
        confluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, configs.get(Common.TARGET_SCHEMA_REGISTRY_URL_CONFIG));

        hortonDeserializer.configure(hortonConfig, false);
        confluentSerializer.configure(confluentConfig, false);
    }

    @Override
    public byte[] serialize(String topic, byte[] data) {
        return confluentSerializer.serialize(topic, hortonDeserializer.deserialize(topic, data));
    }

    @Override
    public void close() {
        hortonDeserializer.close();
        confluentSerializer.close();
    }
}
