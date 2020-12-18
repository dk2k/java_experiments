package delivery;

import java.util.*;

public class CityGraph {
    private Map<City, List<City>> adjVertices;

    public CityGraph() {
        this.adjVertices = new HashMap<City, List<City>>();
    }

    public void addVertex(City vertex) {
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(City src, City dest) {
        adjVertices.get(src).add(dest);
    }

    public Set<City> dfsWithoutRecursion(City start) {
        Set<City> result = new HashSet<>();
        Stack<City> stack = new Stack<City>();
        Map<City, Boolean> isVisited = new HashMap<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            City current = stack.pop();
            isVisited.put(current, true);
            result.add(current);
            //visit(current);
            for (City dest : adjVertices.get(current)) {
                if (isVisited.get(dest) == null || !isVisited.get(dest))
                    stack.push(dest);
            }
        }
        return result;
    }

    // returns collection of cities reachable from the start City
    public Set<City> dfs(City start) {
        return dfsWithoutRecursion(start);
    }

    /*private void dfsRecursive(City current, Map<City, Boolean> isVisited) {
        isVisited.put(current, true);
        visit(current);
        for (City dest : adjVertices.get(current)) {
            if (isVisited.get(dest) == null || !isVisited.get(dest))
                dfsRecursive(dest, isVisited);
        }
    }*/

    /*public List<City> topologicalSort(City start) {
        LinkedList<City> result = new LinkedList<>();
        //boolean[] isVisited = new boolean[adjVertices.size()];
        Map<City, Boolean> isVisited = new HashMap<>();
        topologicalSortRecursive(start, isVisited, result);
        return result;
    }*/

    /*private void topologicalSortRecursive(City current, Map<City, Boolean> isVisited, LinkedList<City> result) {
        isVisited.put(current, true);
        for (City dest : adjVertices.get(current)) {
            if (isVisited.get(dest) == null || !isVisited.get(dest))
                topologicalSortRecursive(dest, isVisited, result);
        }
        result.addFirst(current);
    }*/

    /*private void visit(City value) {
        System.out.print(" " + value);
    }*/

    public static void main(String[] args) {
        City city1 = new City("Armburg");
        City city2 = new City("Harboro");
        City city3 = new City("Serberg");
        City city4 = new City("Baden-baden");

        CityGraph cityGraph = new CityGraph();
        cityGraph.addVertex(city1);
        cityGraph.addVertex(city2);
        cityGraph.addVertex(city3);
        cityGraph.addVertex(city4);

        cityGraph.addEdge(city1, city2);
        cityGraph.addEdge(city2, city3);
        cityGraph.addEdge(city3, city1);
        //cityGraph.addEdge(city2, city4); // not connected to the graph - located on an island

        System.out.println("Reachable cities from Armburg:");
        Set<City> reachable = cityGraph.dfs(city1);
        for (City city : reachable) {
            System.out.println(city);
        }

        System.out.println("Reachable cities from Baden-baden:");
        reachable = cityGraph.dfs(city4);
        for (City city : reachable) {
            System.out.println(city);
        }
    }
}
