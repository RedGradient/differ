package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.HashMap;
import java.util.TreeMap;

public class Formatter {
    public static String render(TreeMap<String, HashMap<String, String>> diff, String formatName) throws Exception {
        switch (formatName) {
            case "stylish" -> {
                return Stylish.stylishFormatter(diff);
            }
            case "plain" -> {
                return Plain.plainFormatter(diff);
            }
            case "json" -> {
                return Json.jsonFormatter(diff);
            }
            default -> {
                var message = String.format("Unknown format name: %s. Can be 'stylish', 'plain' or 'json'",
                        formatName);
                throw new Exception(message);
            }
        }
    }
}
