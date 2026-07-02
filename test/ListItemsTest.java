import airhacks.catalog.boundary.Swag;
import java.nio.file.Files;

// R2: List items
void main() throws Exception {

    // R2.2 — While the catalog is empty, listing returns an empty result.
    var empty = freshCatalog();
    if (!empty.list().isEmpty())
        throw new AssertionError("R2.2 — expected an empty list for an empty catalog, got " + empty.list());

    // R2.1 — Listing returns every stored item.
    var catalog = freshCatalog();
    catalog.add("duke-sticker", "sticker", "M", "10");
    catalog.add("airhacks-mug", "mug", "N/A", "5");
    if (catalog.list().size() != 2)
        throw new AssertionError("R2.1 — expected 2 items but got " + catalog.list());
}

Swag freshCatalog() throws Exception {
    var file = Files.createTempFile("swag", ".tsv");
    return new Swag(file);
}
