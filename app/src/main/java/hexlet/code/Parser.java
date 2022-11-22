package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    static Map parseJson(String data) throws Exception {
        var objectMapper = new ObjectMapper();
        Map<String, Object> dataMap = objectMapper.readValue(data, HashMap.class);
        return stringifyMapValues(dataMap);
    }

    static Map parseYaml(String data) throws Exception {
        var objectMapper = new YAMLMapper();
        Map<String, Object> dataMap = objectMapper.readValue(data, HashMap.class);
        return stringifyMapValues(dataMap);
    }

    private static Map stringifyMapValues(Map<String, Object> dataMap) throws Exception {
        HashMap<String, String> result = new HashMap<>();
        for (var key : dataMap.keySet()) {
            var value = dataMap.get(key);
            try {
                result.put(key, String.valueOf(value));
            } catch (Exception e) {
                throw new Exception("Unexpected object type");
            }
        }

        return result;
    }
}
