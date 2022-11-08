package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.TreeMap;

public class JsonFormatter {
    public static String jsonFormatter(TreeMap<String, LinkedList<String[]>> diff) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }
}
