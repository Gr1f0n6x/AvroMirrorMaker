package avro.mirrormaker;

import com.hortonworks.registries.schemaregistry.client.SchemaRegistryClient;
import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroSerializer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.util.HashMap;
import java.util.Map;

public class ConfluentToHortonSerializer implements AvroSerializer {
    private KafkaAvroDeserializer confluentDeserializer;
    private KafkaAvroSerializer hortonSerializer;

    public ConfluentToHortonSerializer() {
        confluentDeserializer = new KafkaAvroDeserializer();
        hortonSerializer = new KafkaAvroSerializer();
    }

    public void configure(String confluentUrl, String hortonUrl) {
        Map<String, Object> confluentConfig = new HashMap<>();
        Map<String, Object> hortonConfig = new HashMap<>();

        confluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, confluentUrl);
        hortonConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), hortonUrl);

        confluentDeserializer.configure(confluentConfig, false);
        hortonSerializer.configure(hortonConfig, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> confluentConfig = new HashMap<>(configs);
        Map<String, Object> hortonConfig = new HashMap<>(configs);

        confluentConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, configs.get(Common.SOURCE_SCHEMA_REGISTRY_URL_CONFIG));
        hortonConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), configs.get(Common.TARGET_SCHEMA_REGISTRY_URL_CONFIG));

        confluentDeserializer.configure(confluentConfig, false);
        hortonSerializer.configure(hortonConfig, false);
    }

    @Override
    public byte[] serialize(String topic, byte[] data) {
        return hortonSerializer.serialize(topic, confluentDeserializer.deserialize(topic, data));
    }

    @Override
    public void close() {
        confluentDeserializer.close();
        hortonSerializer.close();
    }
}
