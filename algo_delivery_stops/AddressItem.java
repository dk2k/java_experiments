package ru.outofrange.algo;

import java.util.Objects;

public class AddressItem {
    private String street;
    private Integer houseNumber;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return houseNumber + " " + street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressItem)) return false;
        AddressItem that = (AddressItem) o;
        return street.equals(that.street) &&
                houseNumber.equals(that.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber);
    }
}
