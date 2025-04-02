package org.example.aviacompany.model;

import java.util.Objects;

public class Aircraft {
    private String model;
    private Manufacturer manufacturer;
    private double fuelCapacity;
    private double currentFuel;
    private int kilometersFlown;

    public Aircraft(String model, Manufacturer manufacturer, double fuelCapacity) {

        this.model = model;
        this.manufacturer = manufacturer;
        this.fuelCapacity = fuelCapacity;
        this.currentFuel = fuelCapacity;
        this.kilometersFlown = 0;
    }
    public Aircraft() {}

    public void fly(int kilometers) {
        double fuelNeeded = kilometers * 0.1;
        if (currentFuel >= fuelNeeded) {
            currentFuel -= fuelNeeded;
            kilometersFlown += kilometers;
        } else {
            throw new IllegalStateException("Not enough fuel to fly " + kilometers + " km");
        }
    }

    public String getModel() {
        return model;
    }
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void refuel(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be positive");
        currentFuel = Math.min(fuelCapacity, currentFuel + amount);
    }

    public void refuelToFull() {
        currentFuel = fuelCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return model.equals(aircraft.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        return  "Model='" + model + "', Manufacturer=" + manufacturer.getName() +
                ", Fuel=" + currentFuel + "/" + fuelCapacity +
                ", KilometersFlown=" + kilometersFlown ;
    }
}
