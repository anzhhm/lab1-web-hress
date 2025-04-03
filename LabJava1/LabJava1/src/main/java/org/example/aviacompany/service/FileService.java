package org.example.aviacompany.service;

import org.example.aviacompany.model.Aircraft;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileService {
    // Sorting options
    public static final int NO_SORT = 2;
    public static final int SORT_BY_MODEL = 1;

    private final ObjectMapper objectMapper;
    private final AircraftService aircraftService = new AircraftService();

    public FileService() {
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void exportAircraftData(List<Aircraft> aircraftList, String filePath, int sortOption) throws IOException {
        if (aircraftList == null || aircraftList.isEmpty()) {
            throw new IllegalArgumentException("Aircraft list cannot be null or empty");
        }
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        // Sort based on the selected option
        List<Aircraft> sortedList = new ArrayList<>(aircraftList);
        switch (sortOption) {
            case SORT_BY_MODEL:
                sortedList.sort(Comparator.comparing(Aircraft::getModel));
                break;
            case NO_SORT:
            default:
                break;
        }

        List<Map<String, Object>> exportData = new ArrayList<>();
        for (Aircraft aircraft : sortedList) {
            Map<String, Object> aircraftMap = new HashMap<>();
            aircraftMap.put("model", aircraft.getModel());
            aircraftMap.put("manufacturer", aircraft.getManufacturer());
            aircraftMap.put("fuelCapacity", aircraft.getFuelCapacity());
            aircraftMap.put("currentFuel", aircraft.getCurrentFuel());
            aircraftMap.put("kilometersFlown", aircraft.getKilometersFlown());
            exportData.add(aircraftMap);
        }

        objectMapper.writeValue(new File(filePath), exportData);
    }

    public List<Aircraft> importAircraftData(String filePath) throws IOException {
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
