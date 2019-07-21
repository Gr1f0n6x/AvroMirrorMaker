package avro.mirrormaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HortonToConfluentHandler extends BaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(HortonToConfluentHandler.class);

    private String hortonSchemaRegistryUrl;
    private String confluentSchemaRegistryUrl;

    public HortonToConfluentHandler(String args) {
        super(args);

        hortonSchemaRegistryUrl = urls[0];
        confluentSchemaRegistryUrl = urls[1];

        logger.info("Horton schema registry url: {}", hortonSchemaRegistryUrl);
        logger.info("Confluent schema registry url: {}", confluentSchemaRegistryUrl);

        serializer = new HortonToConfluentSerializer();
        serializer.configure(hortonSchemaRegistryUrl, confluentSchemaRegistryUrl);
    }
}
