package ru.outofrange.algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PathSearcher {

    public static DeliveryTaskDescription processInputFile(String inputFilePath) {
        DeliveryTaskDescription description = new DeliveryTaskDescription();

        int counter = 0;
        Pattern pattern = Pattern.compile("^\\s*(\\d+)\\s*(\\S+)\\s*(\\S+)\\s*$");
        //                                      number    Guy        st

        File statesFile = new File(inputFilePath);

        try (BufferedReader br = new BufferedReader(new FileReader(statesFile))) {
            String line;

            while ((line = br.readLine()) != null) {

                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {

                    System.out.println(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));

                    AddressItem addressItem = new AddressItem();
                    addressItem.setHouseNumber(Integer.valueOf(matcher.group(1)));
                    addressItem.setStreet(matcher.group(2) + " " + matcher.group(3));

                    boolean result = description.getAddressItems().add(addressItem);
                    if (!result) {
                        System.out.println("can't add item " + addressItem);
                    }

                }

                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(counter + " " + description.getAddressItems().size());
        return description;
    }

    public FoundResult process(Map<String, Set<AddressItem>> map, int radius) {
        FoundResult foundResult = new FoundResult();
        map.entrySet().stream().forEach(mapEntry -> {
            System.out.println("Processing street " + mapEntry.getKey());
            // if there is at least one house for this street in the delivery task, we need to stop at this street
            while (mapEntry.getValue().size() > 0) {
                // the house numbers are ordered ascending
                // first stop will be at point lowest house number + (radius-1)
                AddressItem stop = new AddressItem();
                AddressItem theFirstInTheStreet = (AddressItem) ((TreeSet) mapEntry.getValue()).first();
                System.out.println("the lowest unvisited house is " + theFirstInTheStreet);
                // if it the only unvisited house in the street, we stop just in front of it
                // to reduce the path of delivery guy on feet :)
                if (mapEntry.getValue().size()==1) {
                    stop.setHouseNumber(theFirstInTheStreet.getHouseNumber());
                } else {
                    // else we stop further to reach as many unvisited houses as possible
                    stop.setHouseNumber(theFirstInTheStreet.getHouseNumber() + (radius - 1));
                }

                stop.setStreet(mapEntry.getKey());
                foundResult.getStops().add(stop);
                // remove the visited houses from the task - find all that are reachable from our stop
                List<AddressItem> reachable = mapEntry.getValue()
                        .stream()
                        .filter(house -> Math.abs(house.getHouseNumber() - stop.getHouseNumber()) < radius)
                        .collect(Collectors.toList());
                System.out.println("stop at " + stop);
                System.out.println("reachable from this stop:");
                reachable.stream().forEach(reach -> System.out.println(reach));
                System.out.println("===");
                // remove all these reachable from the delivery task
                mapEntry.getValue().removeAll(reachable);
            }
            System.out.println("***************************");
        });
        return foundResult;
    }
}
