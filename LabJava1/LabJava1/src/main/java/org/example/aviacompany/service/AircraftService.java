package org.example.aviacompany.service;

import org.example.aviacompany.model.Aircraft;
import org.example.aviacompany.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;


public class AircraftService {
    private List<Aircraft> aircraftList = new ArrayList<>();

    public void addAircraft(Aircraft aircraft) {
        aircraftList.add(aircraft);
    }
    public void addAircrafts(List<Aircraft> newAircrafts) {
        aircraftList.addAll(newAircrafts);
    }

    public Aircraft findByModel(String model) {
        return aircraftList.stream()
                .filter(a -> a.getModel().equalsIgnoreCase(model))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aircraft not found"));
    }

    public boolean existsByModel(String model) {
        return aircraftList.stream()
                .anyMatch(a -> a.getModel().equalsIgnoreCase(model));
    }

    public void updateAircraftModel(String oldModel, String newModel) {
        Aircraft aircraft = findByModel(oldModel);
        aircraft.setModel(newModel);
    }

    public void updateAircraftManufacturer(String model, Manufacturer newManufacturer) {
        Aircraft aircraft = findByModel(model);
        aircraft.setManufacturer(newManufacturer);
    }

    public void removeAircraft(String model) {
        aircraftList.removeIf(a -> a.getModel().equalsIgnoreCase(model));
    }

    public List<Aircraft> getAll() {
        return aircraftList;
    }

}
