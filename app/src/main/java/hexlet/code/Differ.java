package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.util.Objects;

public class Differ {

    public static JsonNode getRootNone(String filePath) throws Exception {
        String extension = null;
        if (filePath.contains(".")) {
            extension = filePath.substring(filePath.indexOf(".") + 1);
        }

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

        File file = new File(filePath);

        return objectMapper.readTree(file);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {

        var formatName = "stylish";

        var node1 = getRootNone(filePath1);
        var node2 = getRootNone(filePath2);

        var diff = Tree.build(node1, node2);
        return Formatter.render(diff, formatName);
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {

        var node1 = getRootNone(filePath1);
        var node2 = getRootNone(filePath2);

        var diff = Tree.build(node1, node2);
        return Formatter.render(diff, formatName);
    }
}
