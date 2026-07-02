import airhacks.catalog.boundary.Swag;
import java.nio.file.Files;
import java.util.List;

// R1: Add an item
void main() throws Exception {

    // R1.1 — When a valid item is added, the BC stores it.
    var catalog = freshCatalog();
    catalog.add("duke-sticker", "sticker", "M", "10");
    if (catalog.list().size() != 1 || catalog.list().getFirst().quantity() != 10)
        throw new AssertionError("R1.1 — expected the added item to be stored, got " + catalog.list());

    // R1.2 — Re-adding an existing name overwrites the item's fields.
    catalog.add("duke-sticker", "sticker", "L", "3");
    var stored = catalog.list();
    if (stored.size() != 1)
        throw new AssertionError("R1.2 — expected an overwrite, not a second entry, but had " + stored.size());
    if (!stored.getFirst().size().equals("L") || stored.getFirst().quantity() != 3)
        throw new AssertionError("R1.2 — expected fields overwritten to size=L quantity=3 but was " + stored.getFirst());

    // R1.3 (missing field) / R1.4 (negative quantity) — invalid adds are rejected.
    record Reject(String req, String name, String category, String size, String quantity) {}
    var rejects = List.of(
            new Reject("R1.3", "",    "sticker", "M", "5"),   // missing name
            new Reject("R1.3", "mug", "",        "M", "5"),   // missing category
            new Reject("R1.3", "mug", "cup",     "",  "5"),   // missing size
            new Reject("R1.3", "mug", "cup",     "M", ""),    // missing quantity
            new Reject("R1.4", "mug", "cup",     "M", "-1")   // negative quantity
    );
    for (var r : rejects) {
        var target = freshCatalog();
        try {
            target.add(r.name(), r.category(), r.size(), r.quantity());
            throw new AssertionError(r.req() + " — expected rejection for " + r);
        } catch (IllegalArgumentException expected) {
            // rejected as required
        }
    }
}

Swag freshCatalog() throws Exception {
    var file = Files.createTempFile("swag", ".tsv");
    return new Swag(file);
}
