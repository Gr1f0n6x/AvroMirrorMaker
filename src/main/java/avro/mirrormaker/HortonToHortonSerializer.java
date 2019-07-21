package avro.mirrormaker;



import com.hortonworks.registries.schemaregistry.client.SchemaRegistryClient;
import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroDeserializer;
import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroSerializer;

import java.util.HashMap;
import java.util.Map;

public class HortonToHortonSerializer implements AvroSerializer {
    private KafkaAvroDeserializer hortonDeserializer;
    private KafkaAvroSerializer hortonSerializer;

    public HortonToHortonSerializer() {
        hortonDeserializer = new KafkaAvroDeserializer();
        hortonSerializer = new KafkaAvroSerializer();
    }

    public void configure(String sourceHortonUrl, String targetHortonUrl) {
        Map<String, Object> firstConfluentConfig = new HashMap<>();
        Map<String, Object> secondConfluentConfig = new HashMap<>();

        firstConfluentConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), sourceHortonUrl);
        secondConfluentConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), targetHortonUrl);

        hortonDeserializer.configure(firstConfluentConfig, false);
        hortonSerializer.configure(secondConfluentConfig, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> firstConfluentConfig = new HashMap<>(configs);
        Map<String, Object> secondConfluentConfig = new HashMap<>(configs);

        firstConfluentConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), configs.get(Common.SOURCE_SCHEMA_REGISTRY_URL_CONFIG));
        secondConfluentConfig.put(SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(), configs.get(Common.TARGET_SCHEMA_REGISTRY_URL_CONFIG));

        hortonDeserializer.configure(firstConfluentConfig, false);
        hortonSerializer.configure(secondConfluentConfig, false);
    }

    @Override
    public byte[] serialize(String topic, byte[] data) {
        return hortonSerializer.serialize(topic, hortonDeserializer.deserialize(topic, data));
    }

    @Override
    public void close() {
        hortonDeserializer.close();
        hortonSerializer.close();
    }
}
