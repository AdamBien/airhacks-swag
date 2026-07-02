import airhacks.catalog.boundary.Swag;
import java.nio.file.Files;

// R3: Remove an item
void main() throws Exception {

    // R3.1 — Removing an existing item deletes it.
    var catalog = freshCatalog();
    catalog.add("duke-sticker", "sticker", "M", "10");
    catalog.remove("duke-sticker");
    if (!catalog.list().isEmpty())
        throw new AssertionError("R3.1 — expected the item removed, but catalog still holds " + catalog.list());

    // R3.2 — Removing an absent item is rejected.
    try {
        catalog.remove("ghost");
        throw new AssertionError("R3.2 — expected rejection removing an absent item");
    } catch (IllegalArgumentException expected) {
        // rejected as required
    }
}

Swag freshCatalog() throws Exception {
    var file = Files.createTempFile("swag", ".tsv");
    return new Swag(file);
}
