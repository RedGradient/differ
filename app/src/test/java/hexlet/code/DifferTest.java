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

        var result = Differ.differ(content1, content2);

        var expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        assertEquals(result, expected);
    }

    @Test
    public void testParse() throws Exception {
        var filePath = resourcesPath + "/json/file1.json";
        var content = Parse.parse(filePath);

        var expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
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

        assertEquals("", Differ.differ("", ""));
    }
}
