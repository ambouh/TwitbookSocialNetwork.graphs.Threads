/*NAME: Andres Mbouh
 *
 *HONOR PLEDGE: I pledge on my honor that I have not given or received any 
 *unauthorized assistance on this assignment/examination. 
 *
 *PURPOSE: The purpose of Twitbook class is to re-use our graph class from 
 *Project #7, in order to simulate a social network where a client can have a 
 *friends list, and these friends can also have their friends list. It is also
 *to get some experience using threads and synchronization, and just a little 
 *experience with networking concepts, which will be used when simultaneously
 *add user or read and exchange data across different web browsers*/
package twitbook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Twitbook {
	// FIELDS
	private Graph<String> socialNetwork;

	// Constructor - initializes a new user
	public Twitbook() {
		socialNetwork = new Graph<String>();
	}

	/*
	 * addUser(String) - returns a boolean adds a user with parameter name. if
	 * user already exists in the graph return false
	 */
	public boolean addUser(String name) {
		boolean result = socialNetwork.isVertex(name); // checks if user exists

		if (!result && name != null) {
			socialNetwork.addVertex(name);// adds user to object
			result = true;
		} else
			result = false;

		return result;

	}

	/* 
	 * getAllUsers() - returns a collection of all users of the current object.
	 * if object doesn't have any users added, an empty collection with no
	 * elements should be returned.
	 */
	public Collection<String> getAllUsers() {
		Collection<String> allUsers = socialNetwork.getVertices();// stores all
		
		// users
		allUsers.remove("");
		allUsers.remove(" ");

		return allUsers;
	}

	/*
	 * friend(String, String) - creates a friendship between users user1 and
	 * user2. - creates users if they don't exist - adds each other to their
	 * friends list and return ture return false if: - users are already friends
	 * - user is adding itself as a friend
	 */
	public boolean friend(String user1, String user2) {
		boolean result = false;

		
		
		// if users are already friends or user is adding itself as a friend
		if ((user1 != null) && (user2 != null) 
				&& !(user1.compareTo(user2) == 0)) {
			createUsers(user1, user2); // helper that creates a user if need be
			boolean isFriendOf1 = socialNetwork.isNeighbor(user1, user2);
			
			if (!isFriendOf1) {
				socialNetwork.addEdge(user1, user2, 1);
				socialNetwork.addEdge(user2, user1, 1);	
				result = true;
			}
			
			
		}
		return result;
	}

	// helper -- it creates a user or both users if they don't exist
	private void createUsers(String user1, String user2) {
		boolean isUser1 = socialNetwork.isVertex(user1);
		boolean isUser2 = socialNetwork.isVertex(user2);

		// adds user1 if it doesn't exist
		if (!isUser1)
			addUser(user1);

		// adds user2 if it doesn't exist
		if (!isUser2)
			addUser(user2);
	}

	/*
	 * getFriends(String) - returns a collection of all of the friends of user.
	 * returns an empty list, if: - user doesn't exist - user doesn't have
	 * friend that exist in his friend list
	 */
	public Collection<String> getFriends(String user) {
		Collection<String> allFriends = socialNetwork.getNeighbors(user);

		return allFriends;
	}

	/*
	 * unfriend(String, String) - returns a boolean after it removes users as
	 * friends. returns false if users are not friends
	 */
	public boolean unfriend(String user1, String user2) {
		boolean areFriends = socialNetwork.isClique(user1, user2);
		boolean result = false;

		if (areFriends) {
			// unfriends users and sets result to true
			socialNetwork.removeEdge(user1, user2);
			socialNetwork.removeEdge(user2, user1);
			result = true;
		}

		return result;
	}

	/*
	 * peopleYouMayKnow(String) - introduces the user to possible friends. it
	 * look thru the friend list of friends, and adds the friends that aren't
	 * mutual, stores each into some type of object of class that implements
	 * Collection interface
	 */
	public Collection<String> peopleYouMayKnow(String user) {
		Collection<String> userFriendsList = getFriends(user);
		Collection<String> pplYouMayKnow = new ArrayList<String>();

		for (String aFriend : userFriendsList) {
			Collection<String> aFriendsList = getFriends(aFriend);

			// goes thru each friends List
			for (String friendOfAFriend : aFriendsList) {
				if (!userFriendsList.contains(friendOfAFriend) 
						&& !user.equals(friendOfAFriend))// if non-mutual
					pplYouMayKnow.add(friendOfAFriend); // adds non-mutual
			}
		}

		return pplYouMayKnow;
	}

	/* readFriendData(String[]) - */
	public void readFriendData(String[] urls) {

		RunThread[] arr = new RunThread[urls.length];

		for (int i = 0; i < urls.length; i++) {

			arr[i] = new RunThread(this);
			arr[i].setUrl(urls[i]);
			arr[i].start();
		}
		for (int j = 0; j < urls.length; j++) {
			try {
				arr[j].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
