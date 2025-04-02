package org.example.aviacompany.service;

import org.example.aviacompany.model.Aircraft;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Експортує список літаків у JSON-файл.
     * @param aircraftList список літаків
     * @param filePath шлях до файлу*/

    public void exportAircraftToJson(List<Aircraft> aircraftList, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), aircraftList);
    }

    /**
     * Імпортує список літаків з JSON-файлу.
     * @param filePath шлях до файлу
     * @return список літаків
     */
    public List<Aircraft> importAircraftFromJson(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Aircraft.class));
    }
}

