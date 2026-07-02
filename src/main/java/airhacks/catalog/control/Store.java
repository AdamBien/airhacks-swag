package airhacks.catalog.control;

import airhacks.catalog.entity.Catalog;
import airhacks.catalog.entity.SwagItem;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

/// Persists the [Catalog] as one tab-separated [SwagItem] per line, so the
/// inventory survives across runs. Insertion order is preserved.
public interface Store {

    static Catalog load(Path file) {
        var items = new ArrayList<SwagItem>();
        if (!Files.exists(file))
            return new Catalog(items);
        try {
            for (var line : Files.readAllLines(file)) {
                if (line.isBlank())
                    continue;
                items.add(SwagItem.fromLine(line));
            }
            return new Catalog(items);
        } catch (IOException ex) {
            throw new UncheckedIOException("cannot read catalog from " + file, ex);
        }
    }

    static void save(Path file, Catalog catalog) {
        try {
            var content = catalog.items().stream()
                    .map(SwagItem::line)
                    .collect(Collectors.joining("\n"));
            Files.writeString(file, content);
        } catch (IOException ex) {
            throw new UncheckedIOException("cannot write catalog to " + file, ex);
        }
    }
}
