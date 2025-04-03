package org.example.aviacompany.service;

import org.example.aviacompany.model.Airline;

import java.util.ArrayList;
import java.util.List;

public class AirlineService {
    private final List<Airline> airlines = new ArrayList<>();

    public void addAirline(Airline airline) {
        airlines.add(airline);
    }

    public Airline findByName(String name) {
        return airlines.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Airline not found: " + name));
    }

    public void removeAirline(String name) {
        airlines.removeIf(a -> a.getName().equalsIgnoreCase(name));
    }

    public void updateAirline(String oldName, String newName) {
        Airline airline = findByName(oldName);
        airline.setName(newName);
    }

    public List<Airline> getAll() {
        return airlines;
    }
}
