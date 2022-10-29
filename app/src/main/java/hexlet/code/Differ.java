package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Differ {
    public static String differ(String text1, String text2) throws Exception {
        return getDiff(text1, text2);
    }

    public static String parse(String readFilePath) throws Exception {

        Path path = Paths.get(readFilePath);
        if (!Files.exists(path)) {
            throw new Exception();
        }

        return Files.readString(path);
    }
    public static String getDiff(String text1, String text2) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = objectMapper.readTree(text1);
        JsonNode node2 = objectMapper.readTree(text2);

        TreeSet <String> fields = new TreeSet<>();

        Iterator<String> it1 = node1.fieldNames();
        while (it1.hasNext()) {
            fields.add(it1.next());
        }
        Iterator<String> it2 = node2.fieldNames();
        while (it2.hasNext()) {
            fields.add(it2.next());
        }

        StringBuilder builder = new StringBuilder("{\n");
        for (var field : fields) {
            if (!node1.has(field)) {
                builder.append(String.format("  + %s: %s\n", field, node2.get(field).toString()));
            } else if (!node2.has(field)) {
                builder.append(String.format("  - %s: %s\n", field, node1.get(field).toString()));
            } else {
                var value1 = node1.get(field).toString();
                var value2 = node2.get(field).toString();
                if (value1.equals(value2)){
                    builder.append(String.format("    %s: %s\n", field, node2.get(field).toString()));
                } else {
                    builder.append(String.format("  - %s: %s\n", field, node1.get(field).toString()));
                    builder.append(String.format("  + %s: %s\n", field, node2.get(field).toString()));
                }
            }
        }
        builder.append("}");

        return builder.toString().replace("\"", "");
    }
}
