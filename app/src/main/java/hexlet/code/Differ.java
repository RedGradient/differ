package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;


public class Differ {

    private static final int SIGN = 0;
    private static final int VALUE = 1;

    public static String stylishFormatter(String text) {
        if (text.startsWith("[") || text.startsWith("{")) {
            return text.replace(",", ", ").replace(":", "=");
        }
        return text;
    }

    public static String plainFormatter(String text) {
        return text;
    }

    public static String generate(String text1, String text2, String formatter) throws Exception {

        if (text1.isEmpty() && text2.isEmpty()) {
            return "";
        }
        var diff = differ(text1, text2);

        StringBuilder builder = new StringBuilder("{\n");
        for (var field : diff.keySet()) {
            var changes = diff.get(field);
            for (var change : changes) {
                var sign = change[SIGN];
                var value = switch (formatter) {
                    case "stylish" -> stylishFormatter(change[VALUE]);
                    case "plain" -> plainFormatter(change[VALUE]);
                    default -> throw new Exception("Unsupported format type");
                };
                var line = String.format("  %s %s: %s\n", sign, field, value);
                builder.append(line);
            }
        }
        builder.append("}");
        return builder.toString().replace("\"", "");
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
