package avro.mirrormaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfluentToConfluentHandler extends BaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConfluentToConfluentHandler.class);

    private String sourceConfluentSchemaRegistryUrl;
    private String targetConfluentSchemaRegistryUrl;

    public ConfluentToConfluentHandler(String args) {
        super(args);

        sourceConfluentSchemaRegistryUrl = urls[0];
        targetConfluentSchemaRegistryUrl = urls[1];

        logger.info("Source confluent schema registry url: {}", sourceConfluentSchemaRegistryUrl);
        logger.info("Target confluent schema registry url: {}", targetConfluentSchemaRegistryUrl);

        serializer = new ConfluentToConfluentSerializer();
        serializer.configure(sourceConfluentSchemaRegistryUrl, targetConfluentSchemaRegistryUrl);
    }
}
