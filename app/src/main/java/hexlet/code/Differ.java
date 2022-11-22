package hexlet.code;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    private static String getFileFormat(File file) {
        var filePath = file.getPath();
        if (!filePath.contains(".")) {
            return null;
        }
        var format = filePath.substring(filePath.lastIndexOf(".") + 1);
        return format.toLowerCase();
    }

    private static Map getData(File file) throws Exception {

        var path = Paths.get(file.getPath());
        var data = Files.readString(path);

        var format = getFileFormat(file);
        var result = switch (format) {
            case "json" -> Parser.parseJson(data);
            case "yml", "yaml" -> Parser.parseYaml(data);
            default -> throw new Exception("Unsupported format");
        };

        return result;
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        var formatName = "stylish";
        return generate(filePath1, filePath2, formatName);
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {

        var file1 = new File(filePath1);
        var file2 = new File(filePath2);

        var map1 = getData(file1);
        var map2 = getData(file2);

        var diff = Tree.build(map1, map2);

        return Formatter.render(diff, formatName);
    }
}
