package hexlet.code.formatters;

import java.util.LinkedList;
import java.util.TreeMap;

public class PlainFormatter {
    public static String plainFormatter(TreeMap<String, LinkedList<String[]>> diff) {

        StringBuilder builder = new StringBuilder();

        for (var field : diff.keySet()) {
            String line = "";
            var changes = diff.get(field);

            if (changes.size() == 1) {
                var sign = changes.get(0)[Utils.SIGN];
                var value = format(changes.get(0)[Utils.VALUE]);

                if (sign.equals("+")) {
                    line = String.format("Property '%s' was added with value: %s\n", field, value);
                } else if (sign.equals("-")) {
                    line = String.format("Property '%s' was removed\n", field);
                }
            } else if (changes.size() == 2) {
                var oldValue = format(changes.get(0)[Utils.VALUE]);
                var newValue = format(changes.get(1)[Utils.VALUE]);

                line = String.format("Property '%s' was updated. From %s to %s\n", field, oldValue, newValue);
            }
            builder.append(line);
        }
        return builder.toString().replace("\"", "");
    }

    public static String format(String json) {
        if ((json.startsWith("[") && json.endsWith("]"))
                || (json.startsWith("{") && json.endsWith("}"))) {
            return "[complex value]";
        }

        if (json.equals("true")
                || json.equals("false")
                || json.equals("null")) {
            return json;
        }

        try {
            Integer.parseInt(json);
            return json;
        } catch (Exception e) {
            return "'" + json + "'";
        }
    }
}
