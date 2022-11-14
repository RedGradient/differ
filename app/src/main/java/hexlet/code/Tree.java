package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tree {

    public static TreeMap<String, HashMap<String, String>> build(JsonNode node1, JsonNode node2) {

        TreeSet<String> fields = new TreeSet<>();

        Iterator<String> it1 = node1.fieldNames();
        while (it1.hasNext()) {
            fields.add(it1.next());
        }
        Iterator<String> it2 = node2.fieldNames();
        while (it2.hasNext()) {
            fields.add(it2.next());
        }

        TreeMap<String, HashMap<String, String>> result = new TreeMap<>();
        for (var field : fields) {

            HashMap<String, String> changes = new HashMap<>();
            result.put(field, changes);

            if (!node1.has(field)) {
                result.get(field).put("+", node2.get(field).toString());
            } else if (!node2.has(field)) {
                result.get(field).put("-", node1.get(field).toString());
            } else {
                var value1 = node1.get(field).toString();
                var value2 = node2.get(field).toString();
                if (value1.equals(value2)) {
                    result.get(field).put("=", value2);
                } else {
                    result.get(field).put("-", value1);
                    result.get(field).put("+", value2);
                }
            }
        }

        return result;
    }
}
