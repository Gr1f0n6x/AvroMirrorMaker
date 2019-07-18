package avro.mirrormaker;

public class HortonToConfluentHandler extends BaseHandler {
    private String hortonSchemaRegistryUrl;
    private String confluentSchemaRegistryUrl;

    public HortonToConfluentHandler(String args) {
        super(args);

        hortonSchemaRegistryUrl = urls[0];
        confluentSchemaRegistryUrl = urls[1];

        serializer = new HortonToConfluentSerializer();
        serializer.configure(hortonSchemaRegistryUrl, confluentSchemaRegistryUrl);
    }
}
