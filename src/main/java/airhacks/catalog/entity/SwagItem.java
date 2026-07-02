package airhacks.catalog.entity;

/// A single piece of swag. The name is the identity; quantity is a non-negative
/// count of stock on hand (zero means out-of-stock).
public record SwagItem(String name, String category, String size, int quantity) {

    public SwagItem {
        if (isBlank(name) || isBlank(category) || isBlank(size))
            throw new IllegalArgumentException("name, category and size are required");
        if (quantity < 0)
            throw new IllegalArgumentException("quantity must be non-negative, was " + quantity);
    }

    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public String line() {
        return String.join("\t", name, category, size, String.valueOf(quantity));
    }

    public static SwagItem fromLine(String line) {
        var parts = line.split("\t", -1);
        return new SwagItem(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
    }
}
