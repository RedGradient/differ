package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Plain {
    public static String render(Map<String, Map<String, String>> diff) {

        StringBuilder builder = new StringBuilder();

        for (var field : diff.keySet()) {

            var changes = diff.get(field);

            if (changes.containsKey("-") && changes.containsKey("+")) {
                var oldValue = toPrettyString(changes.get("-"));
                var newValue = toPrettyString(changes.get("+"));
                var changeLog = String.format("Property '%s' was updated. From %s to %s",
                        field, oldValue, newValue);
                builder.append(changeLog);
            } else if (changes.containsKey("-")) {
                builder.append(String.format("Property '%s' was removed", field));
            } else if (changes.containsKey("+")) {
                var value = toPrettyString(changes.get("+"));
                builder.append(String.format("Property '%s' was added with value: %s", field, value));
            } else {
                continue;
            }

            builder.append("\n");
        }

        var trailingNewLineIndex = builder.lastIndexOf("\n");
        builder.replace(trailingNewLineIndex, builder.length(), "");

        return builder.toString();
    }

    private static String toPrettyString(String json) {
        if ((json.startsWith("[") && json.endsWith("]"))
                || (json.startsWith("{") && json.endsWith("}"))) {
            return "[complex value]";
        }

        if (List.of("true", "false", "null").contains(json)) {
            return json;
        }

        try {
            Double.parseDouble(json);
        } catch (NumberFormatException nfe) {
            return "'" + json + "'";
        }

        return json;
    }
}
