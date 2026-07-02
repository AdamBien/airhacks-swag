import airhacks.catalog.boundary.Swag;
import java.nio.file.Files;

// R4: Persistence
void main() throws Exception {

    // R4.1 — Items survive across a reopen in a later run.
    var file = Files.createTempFile("swag", ".tsv");
    var first = new Swag(file);
    first.add("duke-sticker", "sticker", "M", "10");

    var reopened = new Swag(file);
    var items = reopened.list();
    if (items.size() != 1
            || !items.getFirst().name().equals("duke-sticker")
            || items.getFirst().quantity() != 10)
        throw new AssertionError("R4.1 — expected the persisted item after reopen but got " + items);
}
