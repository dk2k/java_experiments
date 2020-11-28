package ru.outofrange.algo;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] arg) {
        String inputFilePath = "D:\\soft\\AlgorythmDeliveryStops\\sample_input.txt";

        int radius = 10;

        PathSearcher pathSearcher = new PathSearcher();
        DeliveryTaskDescription description = pathSearcher.processInputFile(inputFilePath);

        // this list may contain duplicates
        List<AddressItem> addressItemList = description.getAddressItems();
        addressItemList.stream().forEach(item -> System.out.println(item));

        System.out.println("---------------------");
        Set<String> uniqueStreets
                = addressItemList.stream().map(AddressItem::getStreet).collect(Collectors.toSet());
        uniqueStreets.stream().forEach(street -> System.out.println(street));
        System.out.println("---------------------");
        // for each street compose a set of unique addresses, ordered by house number
        Map<String, Set<AddressItem>> map = addressItemList
                .stream()
                .collect(Collectors.groupingBy(AddressItem::getStreet,
                        Collectors.toCollection(()-> new TreeSet<>(new AddressItemComparator()))));
        System.out.println(map);
        System.out.println("---------------------");

        FoundResult foundResult = pathSearcher.process(map, radius);

        System.out.println("Needed stops");
        foundResult.getStops().stream().forEach(stop-> System.out.println(stop));

    }


}
