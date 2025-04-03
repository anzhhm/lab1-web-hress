package org.example.aviacompany.model;

import java.util.Objects;

public class Manufacturer {
    private String name;
    private String country;

    public Manufacturer() {}

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Name='" + name + "', Country='" + country + "'}";
    }
}

