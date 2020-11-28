package ru.outofrange.algo;

import java.util.ArrayList;
import java.util.List;

public class FoundResult {
    private List<AddressItem> stops;

    public List<AddressItem> getStops() {
        return stops;
    }

    public FoundResult() {
        stops = new ArrayList<>();
    }
}
