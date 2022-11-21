package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

public class Tree {

    public static Map build(Map<String, String> map1, Map<String, String> map2) {

        TreeSet<String> fields = new TreeSet<>(map1.keySet());
        fields.addAll(map2.keySet());

        TreeMap<String, HashMap<String, String>> result = new TreeMap<>();
        for (var field : fields) {

            HashMap<String, String> changes = new HashMap<>();
            result.put(field, changes);

            if (!map1.containsKey(field)) {
                result.get(field).put("+", map2.get(field));
            } else if (!map2.containsKey(field)) {
                result.get(field).put("-", map1.get(field));
            } else {
                var value1 = map1.get(field);
                var value2 = map2.get(field);
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
