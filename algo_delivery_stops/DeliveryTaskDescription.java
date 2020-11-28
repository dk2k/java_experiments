package ru.outofrange.algo;

import java.util.ArrayList;
import java.util.List;

public class DeliveryTaskDescription {
    private List<AddressItem> addressItems;

    public List<AddressItem> getAddressItems() {
        return addressItems;
    }

    public DeliveryTaskDescription() {
        this.addressItems = new ArrayList<>();
    }
}
