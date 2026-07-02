package airhacks.catalog.boundary;

import airhacks.catalog.control.Store;
import airhacks.catalog.entity.Catalog;
import airhacks.catalog.entity.SwagItem;
import java.nio.file.Path;
import java.util.List;

/// Entry point to the swag inventory. Loads the [Catalog] from its backing file on
/// construction and rewrites it after every change, so a later run sees prior items.
public class Swag {

    final Path file;
    final Catalog catalog;

    public Swag(Path file) {
        this.file = file;
        this.catalog = Store.load(file);
    }

    /// `add-item`: store a swag item, overwriting any item with the same name.
    public SwagItem add(String name, String category, String size, String quantity) {
        var item = new SwagItem(name, category, size, parseQuantity(quantity));
        catalog.put(item);
        Store.save(file, catalog);
        return item;
    }

    /// `list-items`: every stored item, in insertion order.
    public List<SwagItem> list() {
        return catalog.items();
    }

    /// `remove-item`: delete an item by name; reject when it is not present.
    public void remove(String name) {
        catalog.remove(name);
        Store.save(file, catalog);
    }

    /// `search-items`: items whose name or category contains the query, ignoring case.
    public List<SwagItem> search(String query) {
        if (query == null || query.isBlank())
            throw new IllegalArgumentException("search query is required");
        return catalog.search(query.trim());
    }

    static int parseQuantity(String quantity) {
        if (quantity == null || quantity.isBlank())
            throw new IllegalArgumentException("quantity is required");
        try {
            return Integer.parseInt(quantity.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("quantity must be a whole number, was '" + quantity + "'");
        }
    }
}
