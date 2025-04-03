package org.example.aviacompany;

import org.example.aviacompany.model.Aircraft;
import org.example.aviacompany.model.Manufacturer;
import org.example.aviacompany.service.AircraftService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AircraftServiceTest {
    private AircraftService service;
    private Manufacturer manufacturer;
    private Aircraft aircraft;

    @BeforeEach
    void setUp() {
        service = new AircraftService();
        manufacturer = new Manufacturer("Boeing", "USA");
        aircraft = new Aircraft("737", manufacturer, 5000);
        service.addAircraft(aircraft);
    }

    @Test
    void testAddAndFindAircraft() {
        assertEquals(aircraft, service.findByModel("737"));
    }

    @Test
    void testFindAircraftNotFound() {
        assertThrows(IllegalArgumentException.class, () -> service.findByModel("747"));
    }

    @Test
    void testRemoveAircraft() {
        service.removeAircraft("737");
        assertThrows(IllegalArgumentException.class, () -> service.findByModel("737"));
    }
}
