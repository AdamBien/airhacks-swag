package airhacks;

import airhacks.catalog.boundary.Swag;
import airhacks.catalog.entity.SwagItem;
import java.nio.file.Path;
import java.util.List;

public interface App {

    String VERSION = "2026-07-02.2";
    Path CATALOG_FILE = Path.of("swag-catalog.tsv");

    static void main(String... args) {
        var swag = new Swag(CATALOG_FILE);
        try {
            run(swag, args);
        } catch (IllegalArgumentException invalid) {
            IO.println("error: " + invalid.getMessage());
            System.exit(1);
        }
    }

    static void run(Swag swag, String... args) {
        switch (args.length == 0 ? "" : args[0]) {
            case "add" -> {
                var item = swag.add(arg(args, 1), arg(args, 2), arg(args, 3), arg(args, 4));
                IO.println("added " + item.name());
            }
            case "list" -> print(swag.list());
            case "search" -> print(swag.search(arg(args, 1)));
            case "remove" -> {
                swag.remove(arg(args, 1));
                IO.println("removed " + arg(args, 1));
            }
            default -> usage();
        }
    }

    private static void print(List<SwagItem> items) {
        if (items.isEmpty()) {
            IO.println("catalog is empty");
            return;
        }
        items.forEach(item -> IO.println("%s | %s | %s | %d"
                .formatted(item.name(), item.category(), item.size(), item.quantity())));
    }

    private static void usage() {
        IO.println("airhacks-swag " + VERSION);
        IO.println("usage:");
        IO.println("  add <name> <category> <size> <quantity>");
        IO.println("  list");
        IO.println("  search <query>");
        IO.println("  remove <name>");
    }

    private static String arg(String[] args, int index) {
        return index < args.length ? args[index] : null;
    }
}
