package org.example.aviacompany;
import org.example.aviacompany.model.Airline;
import org.example.aviacompany.service.AirlineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirlineServiceTest {
    private AirlineService service;
    private Airline airline;

    @BeforeEach
    void setUp() {
        service = new AirlineService();
        airline = new Airline("SkyFly");
        service.addAirline(airline);
    }

    @Test
    void testAddAndFindAirline() {
        assertEquals(airline, service.findByName("SkyFly"));
    }

    @Test
    void testFindAirlineNotFound() {
        assertThrows(IllegalArgumentException.class, () -> service.findByName("NotExist"));
    }

    @Test
    void testUpdateAirline() {
        service.updateAirline("SkyFly", "CloudJet");
        assertEquals("CloudJet", service.findByName("CloudJet").getName());
    }

    @Test
    void testRemoveAirline() {
        service.removeAirline("SkyFly");
        assertThrows(IllegalArgumentException.class, () -> service.findByName("SkyFly"));
    }
}