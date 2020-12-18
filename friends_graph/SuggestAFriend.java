package friends;

import java.util.*;

public class SuggestAFriend {

	/**
	 * Given social network g and name s, returns an ArrayList of all
	 * the names that this social network would suggest s may know
	 * @param g
	 * @param s
	 */
	public static ArrayList<String> suggest(Graph g, String s) {
		// First, let's make sure name s exists in this graph g
		if (g.containsName(s) == false) {
			System.err.println(s + " does not exist in this graph");
			return null;
		}

		// Find and store the friends of s
		Set<String> friendsOfSSet = new HashSet<>(); // hash set prevents duplicates
		friendsOfSSet.addAll(g.getNbrs(s));
		List<String> friendsOfS = new ArrayList<>();
		friendsOfS.addAll(g.getNbrs(s));
		/*if (friendsOfS.size() > friendsOfSSet.size()) {
			System.out.println("! duplicates found " + friendsOfS.size() + " " + friendsOfSSet.size());
		}*/
		//System.out.println("friendsOfS " + friendsOfS.size());

		// Find the list of friends of the friends of s
		Set<String> friendsOfFriendsSet = new HashSet<>();
		for (String friend : friendsOfS) {
			Set<String> currentSet = new HashSet<>();
			currentSet.addAll(g.getNbrs(friend));

			for (String friendOfFriend : currentSet) {
				if (!friendOfFriend.equals(s) && !friendsOfS.contains(friendOfFriend)) {
					// Don't forget to exclude s from this list
					friendsOfFriendsSet.add(friendOfFriend);
				}
			}
		}

        List<String> friendsOfFriends = new ArrayList<>();
        friendsOfFriends.addAll(friendsOfFriendsSet);
		//System.out.println(">> friendsOfFriends " + friendsOfFriends.size());

		// now we need a list associated with a name, HashMap prevents duplicates
		// If we use List<List<String>> it can have duplicate List<String> and in the loop in line 67 we will carry out unnecessary iterations
		Map<String, List<String>> friendsOfFriendsOfFriends = new HashMap<>();
		for (String friend : friendsOfFriends) {
			List<String> currentList = g.getNbrs(friend);
			//System.out.println("for name " + friend + " currentList's = " + currentList);
			friendsOfFriendsOfFriends.put(friend, currentList);
		}

		int maxCommonFriends = 0;

		// If we store the number if common friends (variable intersectionSize in this loop),
		// then we don't need to iterate through friendsOfFriendsOfFriends again. For every name we store the corresponding number of common friends.
		// We find the maximal number of common friends and store it in a auxilliary variable maxCommonFriends.
		// We don't know the maximum till we don't completely iterate through friendsOfFriendsOfFriends.

		// Of all the friends of friends of s, find the ones with
		// the largest number of common friends with s
		Map<String, Integer> mapWithCommonFriendsNumber = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : friendsOfFriendsOfFriends.entrySet()) {
			List<String> intersection = findIntersectionOfLists(friendsOfS, entry.getValue());
			Integer intersectionSize = intersection.size();
			mapWithCommonFriendsNumber.put(entry.getKey(), intersectionSize);
			if (intersectionSize > maxCommonFriends) {
				maxCommonFriends = intersectionSize;
			}
		}
		//System.out.println("max intersection size " + maxCommonFriends);

		ArrayList<String> result = new ArrayList<>();

		// Now we iterate through map mapWithCommonFriendsNumber and find names with the maximum common friend number
		for (Map.Entry<String, Integer> entry : mapWithCommonFriendsNumber.entrySet()) {
			if (entry.getValue().equals(maxCommonFriends)) {
				//System.out.println(entry.getKey() + " has " + maxCommonFriends + " common friends with " + s);
				result.add(entry.getKey());
			}
		}

		// return an ArrayList of all the names with most common friends
		return result;
	}

	// common friends are evaluated as intersection of lists of friends i.e. the names present in both Lists
	private static List<String> findIntersectionOfLists(List<String> list1, List<String> list2) {
		List<String> list = new ArrayList<>();
		for (String t : list1) {
			if(list2.contains(t)) {
				list.add(t);
			}
		}
		//System.out.println("searching intersection for: " + list1 + " ||| " + list2);
		//System.out.println("intersection's size " + list.size());

		return list;
	}

}
