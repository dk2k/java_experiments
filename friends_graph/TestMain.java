package friends;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/* This class tests your SuggestAFriend code and also provides the
 * method for reading a graph from a file.
 * 
 * No edits needed here, but you may edit it for your
 * troubleshooting process.
 */

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException {
		// If your code works, you should be able to run all 4 of
		// these lines and all queries in one go

		Graph g1 = readGraph("graph1.txt");
		Graph g2 = readGraph("graph2.txt");
		Graph g3 = readGraph("graph3.txt");
		Graph g4 = readGraph("graph4.txt");

		// This will view the adjacency list of the graph
		// System.out.println(g1);

		// Queries
		System.out.println(SuggestAFriend.suggest(g1, "Logan"));
		System.out.println(SuggestAFriend.suggest(g1, "Sophia"));
		System.out.println(SuggestAFriend.suggest(g2, "Thomas"));
		System.out.println(SuggestAFriend.suggest(g2, "Dylan"));
		System.out.println(SuggestAFriend.suggest(g3, "Troy"));
		System.out.println(SuggestAFriend.suggest(g3, "Kendra"));
		System.out.println(SuggestAFriend.suggest(g4, "Lucy"));

		// g1: suggest(Logan) -> Chloe
		// g1: suggest(Sophia) -> Mason
		// g2: suggest(Thomas) -> Dylan
		// g2: suggest(Dylan) -> Paisley
		// g3: suggest(Troy) -> Adriel
		// g3: suggest(Kendra) -> [Olive, Weston]
		// g4: suggest(Lucy) -> Vanessa	
	}
	/**
	 * This function reads a list of edges from a given filename
	 * and returns a Graph of this social network.
	 * DO NOT EDIT THIS METHOD.
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Graph readGraph(String filename) throws FileNotFoundException {

		FileReader f = new FileReader(filename);
		Scanner sc = new Scanner(f);

		Graph g = new Graph();
		while (sc.hasNext()) {
			String u = sc.next();
			String v = sc.next();
			g.add(u);
			g.add(v);
			g.add(u,v);
		}
		sc.close();
		return g;
	}
}
