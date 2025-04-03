package org.example.aviacompany.service;

import org.example.aviacompany.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerService {
    private List<Manufacturer> manufacturers = new ArrayList<>();

    public void addManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    public Manufacturer findByName(String name) {
        return manufacturers.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void removeManufacturer(String name) {
        manufacturers.removeIf(m -> m.getName().equalsIgnoreCase(name));
    }

    public void updateManufacturerName(String oldName, String newName) {
        Manufacturer manufacturer = findByName(oldName);
        manufacturer.setName(newName);
    }

    public void updateManufacturerCountry(String newCountry, String name) {
        Manufacturer manufacturer = findByName(name);
        manufacturer.setCountry(newCountry);
    }

    public List<Manufacturer> getAll() {
        return manufacturers;
    }
}
