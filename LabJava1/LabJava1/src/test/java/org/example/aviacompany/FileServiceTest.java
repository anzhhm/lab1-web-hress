package org.example.aviacompany;

import org.example.aviacompany.model.Aircraft;
import org.example.aviacompany.service.FileService;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;
    private final String testFilePath = "test_aircraft.json";
    private List<Aircraft> testAircraftList;

    @BeforeEach
    void setUp() {
        fileService = new FileService();

        testAircraftList = List.of(
                new Aircraft("A320", null, 5000),
                new Aircraft("B737", null, 6000),
                new Aircraft("B787", null, 12000)
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    void testExportAircraftDataWithoutSorting() throws IOException {
        fileService.exportAircraftData(testAircraftList, testFilePath, FileService.NO_SORT);
        File file = new File(testFilePath);
        assertTrue(file.exists());
    }

    @Test
    void testExportAircraftDataSortedByModel() throws IOException {
        fileService.exportAircraftData(testAircraftList, testFilePath, FileService.SORT_BY_MODEL);
        List<String> content = Files.readAllLines(Paths.get(testFilePath));
        assertTrue(content.get(1).contains("A320"));
        assertTrue(content.get(2).contains("B737"));
    }



    @Test
    void testExportAircraftDataWithNullList() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> fileService.exportAircraftData(null, testFilePath, FileService.NO_SORT));
        assertEquals("Aircraft list cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testExportAircraftDataWithInvalidPath() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> fileService.exportAircraftData(testAircraftList, "", FileService.NO_SORT));
        assertEquals("File path cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testImportAircraftData() throws IOException {
        fileService.exportAircraftData(testAircraftList, testFilePath, FileService.NO_SORT);
        List<Aircraft> importedAircraft = fileService.importAircraftData(testFilePath);

        assertNotNull(importedAircraft);
        assertEquals(3, importedAircraft.size());
        assertEquals("A320", importedAircraft.get(0).getModel());
    }

    @Test
    void testImportAircraftDataFileNotFound() {
        IOException thrown = assertThrows(IOException.class,
                () -> fileService.importAircraftData("nonexistent.json"));
        assertTrue(thrown.getMessage().contains("File not found"));
    }

    @Test
    void testImportAircraftDataWithInvalidPath() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> fileService.importAircraftData(null));
        assertEquals("File path cannot be null or empty", thrown.getMessage());
    }
}
