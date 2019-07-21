package avro.mirrormaker;

import kafka.consumer.BaseConsumerRecord;
import kafka.tools.MirrorMaker;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.RecordBatch;

import java.util.Collections;
import java.util.List;

public abstract class BaseHandler implements MirrorMaker.MirrorMakerMessageHandler {
    protected String[] urls;
    protected AvroSerializer serializer;

    public BaseHandler(String args) {
        urls = args.split(",");

        if (urls.length != 2) {
            throw new IllegalArgumentException("Incorrect urls. Urls format: http://host:port,http://host:port");
        }
    }

    @Override
    public List<ProducerRecord<byte[], byte[]>> handle(BaseConsumerRecord record) {
        Long timestamp = record.timestamp() == RecordBatch.NO_TIMESTAMP ? null : record.timestamp();
        return Collections.singletonList(new ProducerRecord<>(
                record.topic(),
                null,
                timestamp,
                record.key(),
                serializer.serialize(record.topic(), record.value()),
                record.headers()
        ));
    }
}
