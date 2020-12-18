package delivery;

public class Order {
    private int weight;
    private City fromCity;
    private City toCity;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "weight=" + weight +
                ", fromCity='" + fromCity.getName() + '\'' +
                ", toCity='" + toCity.getName() + '\'' +
                '}';
    }
}
