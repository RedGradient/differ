package hexlet.code.formatters;

import java.util.LinkedList;
import java.util.TreeMap;

public class StylishFormatter {
    public static String stylishFormatter(TreeMap<String, LinkedList<String[]>> diff) {
        StringBuilder builder = new StringBuilder("{\n");
        for (var field : diff.keySet()) {
            var changes = diff.get(field);
            for (var change : changes) {
                var sign = change[Utils.SIGN];
                var value = toPrettyString(change[Utils.VALUE]);
                var line = String.format("  %s %s: %s\n", sign, field, value);
                builder.append(line);
            }
        }
        builder.append("}");
        return builder.toString().replace("\"", "");
    }

    public static String toPrettyString(String text) {
        if (text.startsWith("[") || text.startsWith("{")) {
            return text.replace(",", ", ").replace(":", "=");
        }
        return text;
    }
}
