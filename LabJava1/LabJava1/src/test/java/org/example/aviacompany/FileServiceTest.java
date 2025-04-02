package org.example.aviacompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aviacompany.service.FileService;
import org.example.aviacompany.model.Manufacturer;
import org.example.aviacompany.model.Aircraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {
    private FileService fileService;
    private final String testFilePath = "test_aircraft.json";
    private List<Aircraft> testAircraftList;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        Manufacturer manufacturer = new Manufacturer("Boeing", "USA");
        testAircraftList = List.of(
                new Aircraft("737", manufacturer, 5000),
                new Aircraft("787", manufacturer, 10000)
        );
    }

    @Test
    void testExportAircraftToJson() throws IOException {
        fileService.exportAircraftToJson(testAircraftList, testFilePath);
        assertTrue(new File(testFilePath).exists());
    }

    @Test
    void testImportAircraftFromJson() throws IOException {
        fileService.exportAircraftToJson(testAircraftList, testFilePath);
        List<Aircraft> importedAircraft = fileService.importAircraftFromJson(testFilePath);

        assertFalse(importedAircraft.isEmpty());
        assertEquals(2, importedAircraft.size());
        assertEquals("737", importedAircraft.get(0).getModel());
        assertEquals("787", importedAircraft.get(1).getModel());
    }

    @Test
    void testImportAircraftFromJsonFileNotFound() {
        assertThrows(IOException.class, () -> fileService.importAircraftFromJson("nonexistent.json"));
    }

    @Test
    void testExportWithMockedObjectMapper() throws IOException {
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        FileService mockFileService = new FileService();
        doNothing().when(objectMapperMock).writeValue(any(File.class), anyList());

        assertDoesNotThrow(() -> mockFileService.exportAircraftToJson(testAircraftList, "mock_aircraft.json"));
        verify(objectMapperMock, times(0)).writeValue(any(File.class), anyList());
    }
}
