package ru.outofrange.algo;

import java.util.Comparator;

public class AddressItemComparator implements Comparator<AddressItem> {
    @Override
    public int compare(AddressItem o1, AddressItem o2) {
        // first we compare street names
        int namesComparison = o1.getStreet().compareTo(o2.getStreet());
        if (namesComparison == 0) {
            // street names are the same, now we compare house numbers
            return o1.getHouseNumber().compareTo(o2.getHouseNumber());
        } else {
            return namesComparison;
        }
    }
}
