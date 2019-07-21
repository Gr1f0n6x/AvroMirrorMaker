package avro.mirrormaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfluentToHortonHandler extends BaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConfluentToHortonHandler.class);

    private String confluentSchemaRegistryUrl;
    private String hortonSchemaRegistryUrl;

    public ConfluentToHortonHandler(String args) {
        super(args);

        confluentSchemaRegistryUrl = urls[0];
        hortonSchemaRegistryUrl = urls[1];

        logger.info("Confluent schema registry url: {}", confluentSchemaRegistryUrl);
        logger.info("Horton schema registry url: {}", hortonSchemaRegistryUrl);

        serializer = new ConfluentToHortonSerializer();
        serializer.configure(confluentSchemaRegistryUrl, hortonSchemaRegistryUrl);
    }
}
