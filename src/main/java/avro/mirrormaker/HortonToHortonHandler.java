package avro.mirrormaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HortonToHortonHandler extends BaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(HortonToHortonHandler.class);

    private String sourceHortonSchemaRegistryUrl;
    private String targetHortonSchemaRegistryUrl;

    public HortonToHortonHandler(String args) {
        super(args);

        sourceHortonSchemaRegistryUrl = urls[0];
        targetHortonSchemaRegistryUrl = urls[1];

        logger.info("Source horton schema registry url: {}", sourceHortonSchemaRegistryUrl);
        logger.info("Target horton schema registry url: {}", targetHortonSchemaRegistryUrl);

        serializer = new ConfluentToConfluentSerializer();
        serializer.configure(sourceHortonSchemaRegistryUrl, targetHortonSchemaRegistryUrl);
    }
}
