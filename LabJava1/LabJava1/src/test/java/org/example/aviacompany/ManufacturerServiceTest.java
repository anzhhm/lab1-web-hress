package org.example.aviacompany;

import org.example.aviacompany.model.Manufacturer;
import org.example.aviacompany.service.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerServiceTest {
    private ManufacturerService service;
    private Manufacturer manufacturer;

    @BeforeEach
    void setUp() {
        service = new ManufacturerService();
        manufacturer = new Manufacturer("Boeing", "USA");
        service.addManufacturer(manufacturer);
    }

    @Test
    void testFindByName() {
        Manufacturer found = service.findByName("Boeing");
        assertNotNull(found);
        assertEquals("Boeing", found.getName());
    }

    @Test
    void testUpdateManufacturerName() {
        service.updateManufacturerName("Boeing", "Airbus");
        Manufacturer updated = service.findByName("Airbus");
        assertNotNull(updated);
        assertEquals("Airbus", updated.getName());
    }

    @Test
    void testRemoveManufacturer() {
        service.removeManufacturer("Boeing");
        assertNull(service.findByName("Boeing"));
    }
}