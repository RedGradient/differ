package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Formatter {
    public static String render(Map diff, String formatName) throws Exception {
        String renderedText = null;
        switch (formatName) {
            case "stylish" -> renderedText = Stylish.render(diff);
            case "plain" -> Plain.render(diff);
            case "json" -> Json.render(diff);
            default -> {
                var message = String.format("Unknown format name: %s. Can be 'stylish', 'plain' or 'json'",
                        formatName);
                throw new Exception(message);
            }
        }
        return renderedText;
    }
}
