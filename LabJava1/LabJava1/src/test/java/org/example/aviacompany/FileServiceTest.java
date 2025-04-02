package org.example.aviacompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aviacompany.model.Aircraft;
import org.example.aviacompany.model.Manufacturer;
import org.example.aviacompany.service.AircraftService;
import org.example.aviacompany.service.FileService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private FileService fileService;
    private AircraftService mockAircraftService;
    private final String testFilePath = "test_aircraft.json";
    private List<Aircraft> testAircraftList;

    @BeforeEach
    void setUp() {
        mockAircraftService = mock(AircraftService.class); // МОКУЄМО, щоб уникнути реальної логіки
        fileService = new FileService(mockAircraftService); // Передаємо мок у FileService

        Manufacturer manufacturer = new Manufacturer("Boeing", "USA");
        testAircraftList = List.of(
                new Aircraft("737", manufacturer, 5000),
                new Aircraft("787", manufacturer, 10000)
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    void testExportAircraftToJson() throws IOException {
        fileService.exportAircraftToJson(testAircraftList, testFilePath);
        File file = new File(testFilePath);
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("737"));
        assertTrue(content.contains("787"));
    }

    @Test
    void testExportAircraftToJsonWithNullList() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> fileService.exportAircraftToJson(null, testFilePath));
        assertEquals("Aircraft list cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testImportAircraftFromJson() throws IOException {
        fileService.exportAircraftToJson(testAircraftList, testFilePath);
        List<Aircraft> importedAircraft = fileService.importAircraftFromJson(testFilePath);

        assertNotNull(importedAircraft);
        assertEquals(2, importedAircraft.size());
        assertEquals("737", importedAircraft.get(0).getModel());
        assertEquals("787", importedAircraft.get(1).getModel());

        // Переконуємось, що addAircrafts() викликається
        verify(mockAircraftService, times(1)).addAircrafts(importedAircraft);
    }

    @Test
    void testImportAircraftFromJsonFileNotFound() {
        IOException thrown = assertThrows(IOException.class,
                () -> fileService.importAircraftFromJson("nonexistent.json"));
        assertTrue(thrown.getMessage().contains("File not found"));
    }

    @Test
    void testImportAircraftFromJsonWithInvalidPath() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> fileService.importAircraftFromJson(null));
        assertEquals("File path cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testExportWithMockedObjectMapper() throws IOException {
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        FileService mockFileService = new FileService(mockAircraftService); // Передаємо мок у FileService
        doNothing().when(objectMapperMock).writeValue(any(File.class), anyList());

        assertDoesNotThrow(() -> mockFileService.exportAircraftToJson(testAircraftList, "mock_aircraft.json"));
    }
}
