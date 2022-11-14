package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;
import java.util.Objects;

public class Parser {

    public static Map parse(String content, String extension) throws Exception {

        ObjectMapper objectMapper;
        switch (Objects.requireNonNull(extension)) {
            case "json" -> {
                objectMapper = new ObjectMapper();
            }
            case "yml", "yaml" -> {
                objectMapper = new YAMLMapper();
            }
            default -> throw new Exception("Unknown extension");
        }

        return objectMapper.readValue(content, Map.class);
    }
}
