package com.fawry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {
    public void ship(List<Shippable> items) {
        if (items.isEmpty()) return;
        Map<String, Group> groups = new LinkedHashMap<>();
        double totalWeight = 0.0;
        for (Shippable item : items) {
            String key = item.getName() + "|" + item.getWeight();
            groups.computeIfAbsent(key, k -> new Group(item.getName(), item.getWeight()));
            groups.get(key).count++;
            totalWeight += item.getWeight();
        }
        System.out.println("** Shipment notice **");
        for (Group g : groups.values()) {
            System.out.printf("%dx %s %.0fg\n", g.count, g.name, g.weight * 1000);
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
    }

    public double calculateShippingFee(List<Shippable> items) {
        return items.isEmpty() ? 0.0 : 30.0; // flat rate
    }

    private static class Group {
        String name;
        double weight;
        int count;
        Group(String name, double weight) {
            this.name = name;
            this.weight = weight;
            this.count = 0;
        }
    }
}
