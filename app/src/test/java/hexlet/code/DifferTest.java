package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    @Test
    public void testDiff() {

        File resourcesPath = new File("src/test/resources");

        String content1 = null;
        String content2 = null;
        var filePath1 = resourcesPath.getAbsolutePath() + "/file1.json";
        var filePath2 = resourcesPath.getAbsolutePath() + "/file2.json";

        try {
            content1 = Differ.parse(filePath1);
            content2 = Differ.parse(filePath2);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

        String result = null;
        try {
            result = Differ.differ(content1, content2);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

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
}
