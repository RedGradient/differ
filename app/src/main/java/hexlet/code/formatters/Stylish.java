package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {
    public static String render(Map<String, Map<String, String>> diff) {
        var builder = new StringBuilder("{\n");
        for (var field : diff.keySet()) {
            var changes = diff.get(field);

            if (changes.containsKey("=")) {
                var value = toPrettyString(changes.get("="));
                builder.append(String.format("    %s: %s\n", field, value));
                continue;
            }

            for (var sign : List.of("-", "+")) {
                if (changes.containsKey(sign)) {
                    var value = toPrettyString(changes.get(sign));
                    builder.append(String.format("  %s %s: %s\n", sign, field, value));
                }
            }
        }

        builder.append("}");

        return builder.toString();
    }

    private static String toPrettyString(String text) {
        var prettyText = text;
        if (prettyText.startsWith("{")) {
            prettyText = prettyText.replace(":", "=");
        }
        return prettyText;
    }
}
