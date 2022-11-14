package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static String resourcesPath;

    @BeforeAll
    public static void beforeAll() {
        resourcesPath = (new File("src/test/resources")).getAbsolutePath();
    }

    @Test
    public void testDefaultValue() {

        var jsonFilePath1 = resourcesPath + "/json/file1.json";
        var jsonFilePath2 = resourcesPath + "/json/file2.json";

        assertDoesNotThrow(() -> {
            new CommandLine(new App()).execute(jsonFilePath1, jsonFilePath2);
        });
    }

    public void testDiffStylishAbstract(String filePath1, String filePath2) throws Exception {

        var expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        var actual = Differ.generate(filePath1, filePath2, "stylish");

        assertEquals(expected, actual);
    }

    public void testDiffPlainAbstract(String filePath1, String filePath2) throws Exception {

        var expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        var actual = Differ.generate(filePath1, filePath2, "plain");

        assertEquals(expected, actual);
    }

    public void testDiffJsonAbstract(String filePath1, String filePath2) throws Exception {

        Path expectedPath = Paths.get(resourcesPath + "/jsonExpected.json");
        var expected = Files.readString(expectedPath);

        var actual = Differ.generate(filePath1, filePath2, "json");

        assertEquals(expected, actual);
    }

//    @Test
//    public void testParse() throws Exception {
//        var filePath = resourcesPath + "/json/file1.json";
//        var content = Parser.parse(filePath);
//
//        var expected = """
//                {
//                  "setting1": "Some value",
//                  "setting2": 200,
//                  "setting3": true,
//                  "key1": "value1",
//                  "numbers1": [1, 2, 3, 4],
//                  "numbers2": [2, 3, 4, 5],
//                  "id": 45,
//                  "default": null,
//                  "checked": false,
//                  "numbers3": [3, 4, 5],
//                  "chars1": ["a", "b", "c"],
//                  "chars2": ["d", "e", "f"]
//                }""";
//
//        assertEquals(expected, content);
//    }

    @Test
    public void testDiff() throws Exception {

        var jsonFilePath1 = resourcesPath + "/json/file1.json";
        var jsonFilePath2 = resourcesPath + "/json/file2.json";

        var yamlFilePath1 = resourcesPath + "/yml/file1.yml";
        var yamlFilePath2 = resourcesPath + "/yml/file2.yml";

        testDiffStylishAbstract(jsonFilePath1, jsonFilePath2);
        testDiffStylishAbstract(yamlFilePath1, yamlFilePath2);

        testDiffPlainAbstract(jsonFilePath1, jsonFilePath2);
        testDiffPlainAbstract(yamlFilePath1, yamlFilePath2);

        testDiffJsonAbstract(jsonFilePath1, jsonFilePath2);
        testDiffJsonAbstract(yamlFilePath1, yamlFilePath2);
    }
}
