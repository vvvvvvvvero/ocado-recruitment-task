package util;

import org.example.util.FileService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTests {

    private FileService fileService;

    @BeforeEach
    public void setUp() {
        fileService = new FileService();
    }

    @Test
    void testReadConfigFileSuccess(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.createFile(tempDir.resolve("config.json"));
        Files.writeString(filePath, "{\"Apples - Spartan\": [\"Express Collection\", \"In-store pick-up\"]}");

        Map<String, List<String>> result = fileService.readConfigFile(filePath.toString());

        assertEquals(1, result.size());
        assertTrue(result.containsKey("Apples - Spartan"));
        assertEquals(Arrays.asList("Express Collection", "In-store pick-up"), result.get("Apples - Spartan"));
    }

    @Test
    void testReadFileNotFound() {
        String nonExistentFilePath = "/src/main/resources/doesnt-exist.json";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileService.readConfigFile(nonExistentFilePath);
        });

        assertTrue(exception.getMessage().contains("File does not exist"));
    }

    @Test
    void testReadFileNullPath() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileService.readConfigFile(null);
        });

        assertTrue(exception.getMessage().contains("Path variable cannot be null"));
    }

    @Test
    void testReadFileMalformedJson(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.createFile(tempDir.resolve("malformed.json"));
        Files.writeString(filePath, "{malformed_json}");
        assertThrows(IllegalArgumentException.class, () -> fileService.readConfigFile(filePath.toString()));
    }



}
