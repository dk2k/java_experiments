package delivery;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Application {

    // 3 delivery services in 3 cities
    // each has a set of cars for delivery
    private static BST[] bstInCity = new BST[3];

    static {
        bstInCity[0] = new BST();
        bstInCity[1] = new BST();
        bstInCity[2] = new BST();
    }

    private static void setupBSTinCity0() {
        bstInCity[0].insert(new Auto(4500, "KAMAZ"));
        bstInCity[0].insert(new Auto(100, "Trabant"));
        bstInCity[0].insert(new Auto(7, "Bicycle"));
        bstInCity[0].insert(new Auto(12, "Upgraded bicycle"));
        bstInCity[0].insert(new Auto(900, "Tesla"));
        bstInCity[0].insert(new Auto(150, "ZAZ"));
        bstInCity[0].insert(new Auto(480, "Kia Ceed"));
    }

    private static void setupBSTinCity1() {
        bstInCity[1].insert(new Auto(4000, "MAZ"));
        bstInCity[1].insert(new Auto(2000, "VW Transporter"));
        bstInCity[1].insert(new Auto(7, "Bicycle"));
        bstInCity[1].insert(new Auto(12, "Upgraded bicycle"));
        bstInCity[1].insert(new Auto(2900, "Boxer"));
        bstInCity[1].insert(new Auto(1000, "Renault Logan"));
    }

    private static void setupBSTinCity2() {
        bstInCity[2].insert(new Auto(6000, "KRAZ"));
        bstInCity[2].insert(new Auto(1100, "Mercedes"));
        bstInCity[2].insert(new Auto(7, "Bicycle"));
        bstInCity[2].insert(new Auto(12, "Upgraded bicycle"));
        bstInCity[2].insert(new Auto(500, "Porsche Kayen"));
        bstInCity[2].insert(new Auto(2400, "Gazelle"));
        bstInCity[2].insert(new Auto(1500, "Citroen"));
    }

    private Queue<Order> orderQueue = new ArrayDeque<>();

    public static void main(String[] args) {

        CityGraph cityGraph = new CityGraph();

        List<City> possibleDestCity = new ArrayList<>();

        // the first island
        City city1 = new City("Armburg");
        City city2 = new City("Harboro");
        City city3 = new City("Serberg");
        City city4 = new City("Barnisville");

        possibleDestCity.add(city1);
        possibleDestCity.add(city2);
        possibleDestCity.add(city3);
        possibleDestCity.add(city4);

        cityGraph.addVertex(city1);
        cityGraph.addVertex(city2);
        cityGraph.addVertex(city3);
        cityGraph.addVertex(city4);

        cityGraph.addEdge(city1, city2);
        cityGraph.addEdge(city2, city3);
        cityGraph.addEdge(city3, city1);
        cityGraph.addEdge(city3, city4);

        // the second island
        City city11 = new City("Berlin");
        City city12 = new City("Hamburg");
        City city13 = new City("Seedorf");
        City city14 = new City("Koblenz");
        City city15 = new City("Koln");

        possibleDestCity.add(city11);
        possibleDestCity.add(city12);
        possibleDestCity.add(city13);
        possibleDestCity.add(city14);
        possibleDestCity.add(city15);

        cityGraph.addVertex(city11);
        cityGraph.addVertex(city12);
        cityGraph.addVertex(city13);
        cityGraph.addVertex(city14);
        cityGraph.addVertex(city15);

        cityGraph.addEdge(city11, city12);
        cityGraph.addEdge(city11, city13);
        cityGraph.addEdge(city11, city14);
        cityGraph.addEdge(city11, city15);

        // the third island
        City city21 = new City("Adelaida");
        City city22 = new City("Sidney");
        City city23 = new City("Melbourne");
        City city24 = new City("Perth");

        possibleDestCity.add(city21);
        possibleDestCity.add(city22);
        possibleDestCity.add(city23);
        possibleDestCity.add(city24);

        cityGraph.addVertex(city21);
        cityGraph.addVertex(city22);
        cityGraph.addVertex(city23);
        cityGraph.addVertex(city24);

        cityGraph.addEdge(city21, city22);
        cityGraph.addEdge(city22, city23);
        cityGraph.addEdge(city23, city24);

        City[] cityWithService = new City[3];
        cityWithService[0] = city1;  // matches BST bstInCity[0]
        cityWithService[1] = city11; // matches BST bstInCity[1]
        cityWithService[2] = city21; // matches BST bstInCity[2]

        setupBSTinCity0();
        setupBSTinCity1();
        setupBSTinCity2();

        Queue<Order> queue = new ArrayDeque<>();

        JFrame frame = new DeliveryServiceGUI(cityWithService, bstInCity, possibleDestCity, cityGraph, queue);
        frame.setResizable(false);
        setFrame(frame, 550, 630);
    }

    public static void setFrame(final JFrame frame, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setTitle(frame.getClass().getSimpleName());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(width, height);
                frame.setVisible(true);
            }
        });
    }
}
