package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

//    @Parameters(index = "0", description = "descr1")
//    private File file1;
//
//    @Parameters(index = "1", description = "descr2")
//    private File file2;

    @Override
    public Integer call() throws Exception {
        return 0;
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
//        System.exit(exitCode);
    }
}
