package avro.mirrormaker;

public class ConfluentToHortonHandler extends BaseHandler {
    private String confluentSchemaRegistryUrl;
    private String hortonSchemaRegistryUrl;

    public ConfluentToHortonHandler(String args) {
        super(args);

        confluentSchemaRegistryUrl = urls[0];
        hortonSchemaRegistryUrl = urls[1];

        serializer = new ConfluentToHortonSerializer();
        serializer.configure(confluentSchemaRegistryUrl, hortonSchemaRegistryUrl);
    }
}
