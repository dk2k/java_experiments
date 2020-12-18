package delivery;

import java.util.Objects;

public class Auto implements Comparable<Auto> {
    //private int maxSpeed; // in km / hour
    private int maxWeight; // in kg
    private String make;
    private Boolean isBusy = false;

    public Auto(int maxWeight, String make) {
        //this.maxSpeed = maxSpeed;
        this.maxWeight = maxWeight;
        this.make = make;
    }

    /*public int getMaxSpeed() {
        return maxSpeed;
    }*/

    /*public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }*/

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Boolean getBusy() {
        return isBusy;
    }

    public void setBusy(Boolean busy) {
        isBusy = busy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auto)) return false;
        Auto auto = (Auto) o;
        return maxWeight == auto.maxWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxWeight);
    }

    @Override
    public int compareTo(Auto o) {
        return Integer.compare(o.maxWeight, this.maxWeight);
    }

    @Override
    public String toString() {
        return "Auto{" +
                "maxWeight=" + maxWeight +
                ", make='" + make + '\'' +
                '}';
    }
}
