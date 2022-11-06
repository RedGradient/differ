package hexlet.code;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;


public class Differ {

    private static final int SIGN = 0;
    private static final int VALUE = 1;

    public static String toPrettyString(String text) {
        if (text.startsWith("[") || text.startsWith("{")) {
            return text.replace(",", ", ").replace(":", "=");
        }
        return text;
    }

    public static String format(String json) {
        if ((json.startsWith("[") && json.endsWith("]")) ||
                (json.startsWith("{") && json.endsWith("}"))) {
            return "[complex value]";
        }

        if (json.equals("true") ||
                json.equals("false") ||
                json.equals("null")) {
            return json;
        }

        try {
            Integer.parseInt(json);
            return json;
        } catch (Exception e) {
            return "'" + json + "'";
        }
    }

    public static String plainFormatter(TreeMap<String, LinkedList<String[]>> diff) {

        StringBuilder builder = new StringBuilder();

        for (var field : diff.keySet()) {
            String line = "";
            var changes = diff.get(field);

            if (changes.size() == 1) {
                var sign = changes.get(0)[SIGN];
                var value = format(changes.get(0)[VALUE]);

                if (sign.equals("+")) {
                    line = String.format("Property '%s' was added with value: %s\n", field, value);
                } else if (sign.equals("-")) {
                    line = String.format("Property '%s' was removed\n", field);
                }
            } else if (changes.size() == 2) {
                var oldValue = format(changes.get(0)[VALUE]);
                var newValue = format(changes.get(1)[VALUE]);

                line = String.format("Property '%s' was updated. From %s to %s\n", field, oldValue, newValue);
            }
            builder.append(line);
        }
        return builder.toString().replace("\"", "");
    }

    public static String stylishFormatter(TreeMap<String, LinkedList<String[]>> diff) {
        StringBuilder builder = new StringBuilder("{\n");
        for (var field : diff.keySet()) {
            var changes = diff.get(field);
            for (var change : changes) {
                var sign = change[SIGN];
                var value = toPrettyString(change[VALUE]);
                var line = String.format("  %s %s: %s\n", sign, field, value);
                builder.append(line);
            }
        }
        builder.append("}");
        return builder.toString().replace("\"", "");
    }

    public static String generate(String text1, String text2, String formatter) throws Exception {

        if (text1.isEmpty() && text2.isEmpty()) {
            return "";
        }
        var diff = differ(text1, text2);

        switch (formatter) {
            case "stylish" -> {
                return stylishFormatter(diff);
            }
            case "plain" -> {
                return plainFormatter(diff);
            }
            default -> {
                var message = String.format("Unknown format name: %s. Can be 'stylish' or 'plain'", formatter);
                throw new Exception(message);
            }
        }
    }

    private static TreeMap<String, LinkedList<String[]>> differ(String text1, String text2)
            throws JsonProcessingException {

        ObjectMapper objectMapper = getMapper(text1);

        TreeSet<String> fields = new TreeSet<>();

        JsonNode node1 = objectMapper.readTree(text1);
        JsonNode node2 = objectMapper.readTree(text2);

        Iterator<String> it1 = node1.fieldNames();
        while (it1.hasNext()) {
            fields.add(it1.next());
        }
        Iterator<String> it2 = node2.fieldNames();
        while (it2.hasNext()) {
            fields.add(it2.next());
        }

        TreeMap<String, LinkedList<String[]>> result = new TreeMap<>();
        for (var field : fields) {
            LinkedList<String[]> values = new LinkedList<>();
            result.put(field, values);
            if (!node1.has(field)) {
                result.get(field).add(new String[]{"+", node2.get(field).toString()});
            } else if (!node2.has(field)) {
                result.get(field).add(new String[]{"-", node1.get(field).toString()});
            } else {
                var value1 = node1.get(field).toString();
                var value2 = node2.get(field).toString();
                if (value1.equals(value2)) {
                    result.get(field).add(new String[]{" ", value2});
                } else {
                    result.get(field).add(new String[]{"-", value1});
                    result.get(field).add(new String[]{"+", value2});
                }
            }
        }

        return result;
    }

    private static ObjectMapper getMapper(String text) {
        ObjectMapper objectMapper;

        try {
            objectMapper = new ObjectMapper();
            objectMapper.readTree(text);
        } catch (Exception e1) {
            objectMapper = new YAMLMapper();
            try {
                objectMapper.readTree(text);
            } catch (Exception e2) {
                objectMapper = null;
            }
        }

        return objectMapper;
    }
}
