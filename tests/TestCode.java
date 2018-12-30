package tests;

// (c) Larry Herman, 2015.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

// This class contains a helper method used in the public tests, and example
// social networks that the tests call methods on.

import twitbook.Twitbook;
import java.util.ArrayList;
import java.util.Collection;

public class TestCode {

  // In various tests we have to check the contents of a Collection returned
  // by a method, but we can't create a Collection that has the expected
  // values and use the equals() method to compare against the Collection,
  // because we don't even know what kind of Collection the methods will
  // return.  This method takes a Collection to check, and an array with the
  // expected values.  It constructs an ArrayList with the array's values
  // (the expected correct values).  Of course an ArrayList is one type of
  // Collection.  Then it uses the Collection containsAll() method to
  // compare the parameter Collection against the ArrayList.  If we have two
  // collections A and B, and A contains all of the elements of B, and B
  // contains all of the elements of A, then we know that they must have all
  // the same elements, and only the same elements.
  public static <T> boolean checkCollection(Collection<T> collection,
                                            T[] array) {
    ArrayList<T> expectedResults= new ArrayList<T>();

    for (T elt : array)
      expectedResults.add(elt);

    return collection.containsAll(expectedResults) &&
           expectedResults.containsAll(collection);
  }

  // example social networks ////////////////////////////////////////////

  // creates a new Twitbook object with several users, but no friendships
  public static Twitbook exampleSocialNetwork1() {
    Twitbook twitbook= new Twitbook();
    String[] users= new String[]{"Ethel", "Franz", "Gertrude", "Wallace",
                                 "Elmer", "Doris"};

    for (String user : users)
      twitbook.addUser(user);

    return twitbook;
  }

  // creates a new Twitbook object with several users and several
  // friendships; note that adding the friendships will add the users also
  public static Twitbook exampleSocialNetwork2() {
    Twitbook twitbook= new Twitbook();

    twitbook.friend("Koala", "Lion");
    twitbook.friend("Koala", "Meerkat");
    twitbook.friend("Koala", "Numbat");
    twitbook.friend("Lion", "Numbat");
    twitbook.friend("Meerkat", "Rhinoceros");
    twitbook.friend("Numbat", "Penguin");
    twitbook.friend("Numbat", "Quokka");
    twitbook.friend("Penguin", "Otter");
    twitbook.friend("Quokka", "Rhinoceros");

    return twitbook;
  }

}
