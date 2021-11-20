package db.dbspringtemplate;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationPropertiesTests {

    private static final Path PATH = Paths.get("src", "main", "resources", "application.properties");

    @Test
    public void checkDevMode() throws IOException {
        Properties props = new Properties();
        props.load(Files.newInputStream(PATH));
        String enableDevMode = props.getProperty("wof.api.enable-dev-mode");
        if ("true".equalsIgnoreCase(enableDevMode)) {
            throw new RuntimeException("Do NOT commit changes to wof.api.enable-dev-mode");
        }
    }

}
