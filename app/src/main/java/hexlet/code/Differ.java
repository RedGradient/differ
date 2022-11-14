package hexlet.code;

public class Differ {

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {

        var content1 = Parse.parse(filePath1);
        var content2 = Parse.parse(filePath2);

        if (content1.isEmpty() && content2.isEmpty()) {
            return "";
        }

        var diff = DiffTree.build(content1, content2);
        return Formatter.formatter(diff, formatName);
    }

}
