import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;

public class Java11FileSystemTest {
    @Rule
    public ErrorCollector collector= new ErrorCollector();

    @Test
    public void wrongJavaTest() throws IOException {
        String userDir = System.getProperty("user.dir");
        System.out.println("original user.dir: " + userDir);
        File pom = new File(/*userDir, */"pom.xml");
        try (BufferedReader reader = new BufferedReader(new FileReader(pom))) {
            long lines = reader.lines().count();
            System.out.println(lines + " lines");
            collector.checkThat("number of lines in parent POM", lines, equalTo(39L));
        }
        System.out.println("original user.dir: " + userDir);

        System.setProperty("user.dir", new File(userDir, "child").getAbsolutePath());
        userDir = System.getProperty("user.dir");
        System.out.println("modified user.dir: " + userDir);
        pom = new File(/*userDir, */"pom.xml");
        try (BufferedReader reader = new BufferedReader(new FileReader(pom))) {
            long lines = reader.lines().count();
            System.out.println(lines + " lines");
            collector.checkThat("number of lines in child POM", lines, equalTo(15L));
        }
        System.out.println("modified user.dir: " + userDir);
        collector.checkThat("should be \"child\" folder", userDir, endsWith("child"));
    }

    @Test
    public void okJavaTest() throws IOException {
        String userDir = System.getProperty("user.dir");
        System.out.println("original user.dir: " + userDir);
        File pom = new File(/*userDir, */"pom.xml");
        try (BufferedReader reader = new BufferedReader(new FileReader(pom))) {
            long lines = reader.lines().count();
            System.out.println(lines + " lines");
            collector.checkThat("number of lines in parent POM", lines, equalTo(39L));
        }
        System.out.println("original user.dir: " + userDir);

        System.setProperty("user.dir", new File(userDir, "child").getAbsolutePath());
        userDir = System.getProperty("user.dir");
        System.out.println("modified user.dir: " + userDir);
        pom = new File(userDir, "pom.xml");
        try (BufferedReader reader = new BufferedReader(new FileReader(pom))) {
            long lines = reader.lines().count();
            System.out.println(lines + " lines");
            collector.checkThat("number of lines in child POM", lines, equalTo(15L));
        }
        System.out.println("modified user.dir: " + userDir);
        collector.checkThat("should be \"child\" folder", userDir, endsWith("child"));
    }
}
