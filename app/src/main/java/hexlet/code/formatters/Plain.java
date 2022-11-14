package hexlet.code.formatters;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Plain {
    public static String plainFormatter(TreeMap<String, HashMap<String, String>> diff) {

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

            if (!field.equals(diff.lastKey())) {
                builder.append("\n");
            }
        }

        return builder.toString().replace("\"", "");
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
