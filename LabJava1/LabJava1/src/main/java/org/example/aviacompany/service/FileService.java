package org.example.aviacompany.service;

import org.example.aviacompany.model.Aircraft;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FileService {
    private final ObjectMapper objectMapper;
    private final AircraftService aircraftService;

    public FileService(AircraftService aircraftService) {
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.aircraftService = Objects.requireNonNull(aircraftService, "AircraftService cannot be null");
    }



    /**
     * Експортує список літаків у JSON-файл.
     * @param aircraftList список літаків
     * @param filePath шлях до файлу
     * @throws IOException у разі помилки запису
     */
    public void exportAircraftToJson(List<Aircraft> aircraftList, String filePath) throws IOException {
        if (aircraftList == null || aircraftList.isEmpty()) {
            throw new IllegalArgumentException("Aircraft list cannot be null or empty");
        }
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        objectMapper.writeValue(new File(filePath), aircraftList);
    }

    /**
     * Імпортує список літаків з JSON-файлу.
     * @param filePath шлях до файлу
     * @return список літаків
     * @throws IOException у разі помилки читання
     */
    public List<Aircraft> importAircraftFromJson(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File not found: " + filePath);
        }

        List<Aircraft> importedAircraft = objectMapper.readValue(file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Aircraft.class));

        aircraftService.addAircrafts(importedAircraft);

        return importedAircraft;
    }
}
