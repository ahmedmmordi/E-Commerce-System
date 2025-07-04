package service;

import entities.interfaces.Shippable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Groups and prints shippable items
public class ShippingService {
    public void shipItems(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        if (items == null || items.isEmpty()) {
            System.out.println("No items to ship.");
            return;
        }

        Map<String, Integer> counts = new HashMap<>();
        Map<String, Double> weights = new HashMap<>();

        // Count item occurrences and store weights by name
        for (Shippable item : items) {
          if (item.getWeight() <= 0) continue;
            counts.put(item.getName(), counts.getOrDefault(item.getName(), 0) + 1);
            weights.put(item.getName(), item.getWeight());
        }

        // Sum total weight and print item counts and weights
        double totalWeight = 0;
        for (String name : counts.keySet()) {
            int count = counts.get(name);
            double singleWeight = weights.get(name);
            double totalItemWeight = singleWeight * count;
            totalWeight += totalItemWeight;

            System.out.printf("%dx %s %.0fg%n", count, name, totalItemWeight * 1000);
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        System.out.println();
    }
}