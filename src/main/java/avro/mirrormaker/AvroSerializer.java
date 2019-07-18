package avro.mirrormaker;

import org.apache.kafka.common.serialization.Serializer;

public interface AvroSerializer extends Serializer<byte[]> {
    void configure(String confluentUrl, String hortonUrl);
}
