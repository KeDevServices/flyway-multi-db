package eu.kedev.blog.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;


public class FlywayConstants {

    public static final String prefix = "/*[";
    public static final String suffix = "]*/";

    public static final List<String> ids =
            unmodifiableList(Arrays.asList("h2", "postgres"));

    public static final Map<String, String> driverToId = new HashMap<String, String>() {{
        put("H2", "h2");
        put("PostgreSQL", "postgres");
    }};

    public static Map<String, String> placeholders(final String chosenId) {
        final Map<String, String> out = new HashMap<>();
        for (final String id: ids) {
            if (id.equals(chosenId)) {
                out.put(id + ":start", "");
                out.put(id + ":end", "");
            } else {
                out.put(id + ":start", "/*");
                out.put(id + ":end", "*/");
            }
        }
        return unmodifiableMap(out);
    }
}
