package airhacks.catalog.entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/// The swag inventory: [SwagItem]s keyed by their unique name, kept in insertion order.
public class Catalog {

    final Map<String, SwagItem> items;

    public Catalog(Collection<SwagItem> initial) {
        this.items = new LinkedHashMap<>();
        initial.forEach(item -> items.put(item.name(), item));
    }

    /// Store an item, overwriting any item that shares its name.
    public void put(SwagItem item) {
        items.put(item.name(), item);
    }

    /// Remove an item by name; reject when it is not present.
    public void remove(String name) {
        if (!items.containsKey(name))
            throw new IllegalArgumentException("no swag item named '" + name + "'");
        items.remove(name);
    }

    public List<SwagItem> items() {
        return List.copyOf(items.values());
    }

    /// Items whose name or category contains the query, ignoring case.
    public List<SwagItem> search(String query) {
        var needle = query.toLowerCase();
        return items.values().stream()
                .filter(item -> item.name().toLowerCase().contains(needle)
                        || item.category().toLowerCase().contains(needle))
                .toList();
    }
}
