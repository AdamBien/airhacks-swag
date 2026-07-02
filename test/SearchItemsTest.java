import airhacks.catalog.boundary.Swag;
import java.nio.file.Files;
import java.util.List;

// R5: Search items
void main() throws Exception {
    var catalog = freshCatalog();
    catalog.add("duke-sticker", "sticker", "M", "10");
    catalog.add("airhacks-lanyard", "accessory", "M", "2");

    // R5.1 (match by name/category, case-insensitive) / R5.2 (no match → empty).
    record Case(String req, String query, int expected) {}
    var cases = List.of(
            new Case("R5.1", "lanyard",   1),   // by name
            new Case("R5.1", "ACCESSORY", 1),   // by category, case-insensitive
            new Case("R5.1", "sticker",   1),   // by category
            new Case("R5.2", "keychain",  0)    // no match → empty result
    );
    for (var c : cases) {
        var results = catalog.search(c.query());
        if (results.size() != c.expected())
            throw new AssertionError("%s — query '%s' expected %d result(s) but got %s"
                    .formatted(c.req(), c.query(), c.expected(), results));
    }

    // R5.3 — a blank query is rejected.
    for (var blank : new String[] {"", "   ", null}) {
        try {
            catalog.search(blank);
            throw new AssertionError("R5.3 — expected rejection for blank query [" + blank + "]");
        } catch (IllegalArgumentException expected) {
            // rejected as required
        }
    }
}

Swag freshCatalog() throws Exception {
    var file = Files.createTempFile("swag", ".tsv");
    return new Swag(file);
}
