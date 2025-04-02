package org.example.aviacompany.main;

import org.example.aviacompany.model.Aircraft;
import org.example.aviacompany.model.Manufacturer;
import org.example.aviacompany.service.AircraftService;
import org.example.aviacompany.service.AirlineService;
import org.example.aviacompany.service.FileService;
import org.example.aviacompany.service.ManufacturerService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AircraftService aircraftService = new AircraftService();
    private static final ManufacturerService manufacturerService = new ManufacturerService();
    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Додати літак");
            System.out.println("2. Переглянути всі літаки");
            System.out.println("3. Видалити літак");
            System.out.println("5. Додати виробника");
            System.out.println("6. Переглянути всіх виробників");
            System.out.println("7. Політ літака");
            System.out.println("8. Дозаправити літак");
            System.out.println("9. Заправити літак повністю");
            System.out.println("10. Експорт літаків у JSON");
            System.out.println("11. Імпорт літаків з JSON");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");;

            if (!scanner.hasNextInt()) {
                System.out.println("Помилка: введіть число.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    Aircraft aircraft = createAircraft();
                    if (aircraft != null && !aircraftService.existsByModel(aircraft.getModel())) {
                        aircraftService.addAircraft(aircraft);
                    } else {
                        System.out.println("Такий літак вже існує");
                    }
                }
                case 2 ->{aircraftService.getAll().forEach(System.out::println);}
                case 3 -> {
                    System.out.print("Модель літака для видалення: ");
                    String model = scanner.nextLine();
                    if (aircraftService.existsByModel(model)) {
                        aircraftService.removeAircraft(model);
                        System.out.println("Літак видалено.");
                    } else {
                        System.out.println("Такого літака не існує");
                    }
                }
                case 5 -> {
                    System.out.print("Назва виробника: ");
                    String name = scanner.nextLine();
                    System.out.print("Країна виробника: ");
                    String country = scanner.nextLine();

                    if (manufacturerService.findByName(name) != null) {
                        System.out.println("Такий виробник уже існує.");
                    } else {
                        manufacturerService.addManufacturer(new Manufacturer(name, country));
                        System.out.println("Виробника додано.");
                    }
                }
                case 6 -> manufacturerService.getAll().forEach(System.out::println);
                case 7 -> {
                    System.out.print("Модель літака для польоту: ");
                    String model = scanner.nextLine();
                    if (!aircraftService.existsByModel(model)) {
                        System.out.println("Літак не знайдено.");
                        continue;
                    }
                    System.out.print("Кількість кілометрів: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Помилка: введіть число.");
                        scanner.next();
                        continue;
                    }
                    int kilometers = scanner.nextInt();
                    scanner.nextLine();
                    Aircraft aircraft = aircraftService.findByModel(model);
                    try {
                        aircraft.fly(kilometers);
                        System.out.println("Літак успішно пролетів " + kilometers + " км.");
                    } catch (IllegalStateException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                }
                case 8 -> {
                    System.out.print("Модель літака для дозаправки: ");
                    String model = scanner.nextLine();
                    if (!aircraftService.existsByModel(model)) {
                        System.out.println("Літак не знайдено.");
                        continue;
                    }
                    System.out.print("Кількість пального для дозаправки: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Помилка: введіть число.");
                        scanner.next();
                        continue;
                    }
                    double fuel = scanner.nextDouble();
                    scanner.nextLine();
                    aircraftService.findByModel(model).refuel(fuel);
                    System.out.println("Літак успішно дозаправлено.");
                }
                case 9 -> {
                    System.out.print("Модель літака для повної заправки: ");
                    String model = scanner.nextLine();
                    if (aircraftService.existsByModel(model)) {
                        aircraftService.findByModel(model).refuelToFull();
                        System.out.println("Літак повністю заправлено.");
                    } else {
                        System.out.println("Літак не знайдено.");
                    }
                }
                case 10 -> {
                    System.out.print("Введіть шлях до файлу JSON для експорту: ");
                    String filePath = scanner.nextLine();
                    try {
                        fileService.exportAircraftToJson(aircraftService.getAll(), filePath);
                        System.out.println("Дані експортовані успішно!");
                    } catch (IOException e) {
                        System.out.println("Помилка експорту: " + e.getMessage());
                    }
                }
                case 11 -> {
                    System.out.print("Введіть шлях до файлу JSON для імпорту: ");
                    String filePath = scanner.nextLine();
                    try {
                        fileService.importAircraftFromJson(filePath).forEach(aircraftService::addAircraft);
                        System.out.println("Дані імпортовані успішно!");
                    } catch (IOException e) {
                        System.out.println("Помилка імпорту: " + e.getMessage());
                    }
                }
                case 0 -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Невідома опція, спробуйте ще раз.");
            }
        }
    }

    private static Aircraft createAircraft() {
        System.out.print("Модель літака: ");
        String model = scanner.nextLine();
        System.out.print("Місткість пального: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Помилка: введіть число.");
            scanner.next();
            return null;
        }
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Виробник: ");
        String manufacturerName = scanner.nextLine();

        Manufacturer manufacturer = manufacturerService.findByName(manufacturerName);

        if (manufacturer == null) { // Перевіряємо, чи виробник існує
            System.out.print("Країна виробника: ");
            String country = scanner.nextLine();
            manufacturer = new Manufacturer(manufacturerName, country);
            manufacturerService.addManufacturer(manufacturer);
        }

        return new Aircraft(model, manufacturer, fuelCapacity);
    }
}
