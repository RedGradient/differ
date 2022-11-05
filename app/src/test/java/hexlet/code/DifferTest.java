package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    public static String resourcesPath;

    @BeforeAll
    public static void beforeAll() {
        resourcesPath = (new File("src/test/resources")).getAbsolutePath();
    }

    public void testDiffAbstract(String filePath1, String filePath2) throws Exception {

        var content1 = Parse.parse(filePath1);
        var content2 = Parse.parse(filePath2);

        var result = Differ.generate(content1, content2, "stylish");

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

        assertEquals(result, expected);
    }

    @Test
    public void testParse() throws Exception {
        var filePath = resourcesPath + "/json/file1.json";
        var content = Parse.parse(filePath);

        var expected = """
                {
                  "setting1": "Some value",
                  "setting2": 200,
                  "setting3": true,
                  "key1": "value1",
                  "numbers1": [1, 2, 3, 4],
                  "numbers2": [2, 3, 4, 5],
                  "id": 45,
                  "default": null,
                  "checked": false,
                  "numbers3": [3, 4, 5],
                  "chars1": ["a", "b", "c"],
                  "chars2": ["d", "e", "f"]
                }""";

        assertEquals(expected, content);
    }

    @Test
    public void testDiffJson() throws Exception {

        var jsonFilePath1 = resourcesPath + "/json/file1.json";
        var jsonFilePath2 = resourcesPath + "/json/file2.json";

        var yamlFilePath1 = resourcesPath + "/yml/file1.yml";
        var yamlFilePath2 = resourcesPath + "/yml/file2.yml";

        testDiffAbstract(jsonFilePath1, jsonFilePath2);
        testDiffAbstract(yamlFilePath1, yamlFilePath2);

        assertEquals("", Differ.generate("", "", "stylish"));
    }

    @Test
    public void testStylishFormatter() {
        var expected1 = "[1, 2, 3, 4]";
        var actual1 = Differ.stylishFormatter("[1,2,3,4]");
        assertEquals(expected1, actual1);

        var expected2 = "{nestedKey=value, isNested=true}";
        var actual2 = Differ.stylishFormatter("{nestedKey:value,isNested:true}");
        assertEquals(expected2, actual2);
    }
}
