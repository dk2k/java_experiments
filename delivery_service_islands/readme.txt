We have 3 services in 3 cities. 
Max accepted weight of package is evaluated as max load of all available vehicles in this service.

Each city with a service is placed on a separate island. That allows to create a graph with 3 insulated subgraphs. For this entire graph we apply depth first search (DFS) - not recursive version utilizes stack. We pass city from which we need to deliver and get the list of cities reachable from this start city. If destination city isn't reachable, we can decide: we don't accept an order.
Brief: each service delivers only on its island.
Graph is undirected - if we add an edge for cities A B, then it means A is reachable from B and B is reachable from A. 
And of course A is reachable from A 
Class CityGraph has main() method for demo and testing.

Each service has several vehicles for deliveries. A car has a mark and max weight (max load). I also added max speed, but it isn't used so far.
Cars are sorted using binary search tree (BST) based on their max load. That means when a service accepts an order to deliver package with weight 95 kg, we can find a vehicle which can take this package. 
Class BST has main() method for demo and testing.
When we consider weigth of the package, we check if we can potentially find a capable car. That means it can be busy at this moment, but will return from its trip. We find best fit car. If load is 100 kg and there are vacant cars with max load 150 kg and 200 kg, we take the car with max 150 kg.

Orders are accepted into a queue. I simulate some delay: an order is taken from queue and the next will be taken after some interval - our only loader-guy is busy ;) Pause is 4 seconds. There is a separate thread to check the queue.

When a car starts delivery, it's marked busy for some period - 8 seconds (way to the destination city and back). For simplicity this period is constant - doesn't depend on the number of edges between the cities in the graph. Delivery is simulated via separate thread - class DeliveryRunnable.


groups of cities on the islands:

        // the first island
        City city1 = new City("Armburg");
        City city2 = new City("Harboro");
        City city3 = new City("Serberg");
        City city4 = new City("Barnisville");
		
        // the second island
        City city11 = new City("Berlin");
        City city12 = new City("Hamburg");
        City city13 = new City("Seedorf");
        City city14 = new City("Koblenz");
        City city15 = new City("Koln");
		
        // the third island
        City city21 = new City("Adelaida");
        City city22 = new City("Sidney");
        City city23 = new City("Melbourne");
        City city24 = new City("Perth");
		
		