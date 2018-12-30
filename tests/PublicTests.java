package tests;

// (c) Larry Herman, 2015.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import twitbook.Twitbook;
import java.util.Collections;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  // the last two tests have the same results, so to make things shorter we
  // just create two static arrays for use in checking both of them
  static String[] expectedUsers;
  static String[][] expectedFriends;

  static {  // static initialization block

    expectedUsers= new String[]{"Adelina", "Clark", "Deborah", "Edward",
                                "Elizabeth", "Emmie", "Evan", "Exie", "Felipe",
                                "Grady", "Hilma", "Katie", "Leila", "Lindsay",
                                "Madaline", "Marvel", "Maude", "Melba",
                                "Miriam", "Pearl", "Philomena", "Phoebe",
                                "Roger", "Susan", "Schuyler", "Shelley",
                                "Valerie", "Vernie", "Zella"};

    // to make things easy we use an array of arrays to store the friends of
    // each user, where the first element is an array of Adelina's friends,
    // the second element is an array of Clark's friends, etc.
    expectedFriends= new String[][]{
      {"Felipe", "Zella", "Lindsay", "Melba"},
      {"Pearl", "Felipe", "Evan"},
      {"Vernie", "Felipe"},
      {"Schuyler", "Miriam"},
      {"Valerie", "Zella", "Leila", "Melba"},
      {"Phoebe", "Zella", "Leila", "Melba"},
      {"Phoebe", "Clark", "Zella", "Philomena"},
      {"Felipe"},
      {"Roger", "Deborah", "Clark", "Adelina", "Exie"},
      {"Schuyler"},
      {"Valerie"},
      {"Susan"},
      {"Elizabeth", "Emmie"},
      {"Susan", "Adelina"},
      {"Susan"},
      {"Valerie", "Schuyler"},
      {"Roger"},
      {"Roger", "Vernie", "Elizabeth", "Adelina", "Emmie"},
      {"Vernie", "Edward"},
      {"Clark"},
      {"Schuyler", "Evan"},
      {"Emmie", "Evan"},
      {"Felipe", "Maude", "Melba"},
      {"Grady", "Philomena", "Edward", "Marvel"},
      {"Susan"},
      {"Madaline", "Katie", "Zella", "Lindsay", "Shelley"},
      {"Elizabeth", "Hilma", "Marvel"},
      {"Deborah", "Miriam", "Melba"},
      {"Elizabeth", "Susan", "Adelina", "Emmie", "Evan"}};

  }

  // Tests that the getAllUsers() method returns the names of all of the
  // users of the social network, that has users but no friend
  // relationships.  Note that this test does not create or use any threads.
  @Test public void testPublic1() {
    Twitbook twitbook= TestCode.exampleSocialNetwork1();

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Doris", "Elmer", "Ethel",
                                                     "Franz", "Gertrude",
                                                     "Wallace"}));
  }

  // Tests that the getFriends() method returns the names of all of the
  // users of a social network.  Note that this test does not create or use
  // any threads.
  @Test public void testPublic2() {
    Twitbook twitbook= TestCode.exampleSocialNetwork2();

    assertTrue(TestCode.checkCollection(twitbook.getFriends("Numbat"),
                                        new String[]{"Koala", "Lion", "Penguin",
                                                     "Quokka"}));
  }

  // Tests that a user cannot add themself as their own friend (that would
  // be just too sad....).  Note that this test does not create or use any
  // threads.
  @Test public void testPublic3() {
    Twitbook twitbook= TestCode.exampleSocialNetwork2();

    twitbook.friend("Numbat", "Numbat");

    assertTrue(TestCode.checkCollection(twitbook.getFriends("Numbat"),
                                        new String[]{"Koala", "Lion", "Penguin",
                                                     "Quokka"}));
  }

  // Tests the unfriend() method.  Note that this test does not create or
  // use any threads.
  @Test public void testPublic4() {
    Twitbook twitbook= TestCode.exampleSocialNetwork2();

    twitbook.unfriend("Numbat", "Lion");

    assertTrue(TestCode.checkCollection(twitbook.getFriends("Numbat"),
                                        new String[]{"Koala", "Penguin",
                                                     "Quokka"}));
  }

  // Tests the peopleYouMayKnow() method.  Note that this test does not
  // create or use any threads.
  @Test public void testPublic5() {
    Twitbook twitbook= TestCode.exampleSocialNetwork2();    
    assertTrue(TestCode.checkCollection(twitbook.peopleYouMayKnow("Rhinoceros"),
                                        new String[]{"Koala", "Numbat"}));
  }

  // Creates one thread, which reads one list of user data, which only
  // contains one user addition, just to ensure that one thread can be
  // created and manipulated correctly.
  @Test public void testPublic6() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends6.html"
      });
    
    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Koala"}));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions.  The test checks the results by calling
  // getAllUsers().
  @Test public void testPublic7() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends7.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Doris", "Elmer", "Ethel",
                                                     "Franz", "Gertrude",
                                                     "Wallace"}));
  }

  // Creates one thread, which reads one list of user data, which only
  // contains one friend relationship.  Note that creating the friend
  // relationship will add or create the two users in the process.  The
  // results are checked by calling getAllUsers() and getFriends().  This
  // also checks that creating a friendship works both ways (both users
  // become friends of each other).
  @Test public void testPublic8() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends8.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Aardvark", "Platypus"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Aardvark"),
                                        new String[]{"Platypus"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Platypus"),
                                        new String[]{"Aardvark"}));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions and several friend relationships.  The results
  // are checked by calling getAllUsers(), and getFriends() on some of the
  // users.
  @Test public void testPublic9() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends9-10.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Koala", "Lion", "Meerkat",
                                                     "Numbat", "Otter",
                                                     "Penguin", "Quokka",
                                                     "Rhinoceros"}));

    assertTrue(TestCode.checkCollection(twitbook.getFriends("Otter"),
                                        new String[]{"Penguin"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Koala"),
                                        new String[]{"Lion", "Numbat",
                                                     "Meerkat"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Numbat"),
                                        new String[]{"Koala", "Lion", "Penguin",
                                                     "Quokka"}));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions and several friend relationships, and calls the
  // peopleYouMayKnow() method.
  @Test public void testPublic10() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends9-10.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.peopleYouMayKnow("Rhinoceros"),
                                        new String[]{"Koala", "Numbat"}));
  }

  // Creates two threads, which each independently read the same list of
  // user data, which contains two user additions for the same person; the
  // duplicate addition, even when performed concurrently by two threads,
  // should have no effect.
  @Test public void testPublic11() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends11.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends11.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Koala"}));
  }

  // Creates two threads, which each independently read the same list of
  // user data, which contains two friend relationships for the same two
  // people; the duplicate friend relationship, even when performed
  // concurrently by two threads, should have no effect.
  @Test public void testPublic12() {
    Twitbook twitbook= new Twitbook();

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends12.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends12.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(),
                                        new String[]{"Aardvark", "Platypus"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Aardvark"),
                                        new String[]{"Platypus"}));
    assertTrue(TestCode.checkCollection(twitbook.getFriends("Platypus"),
                                        new String[]{"Aardvark"}));
  }

  // Creates two threads, which each read a list of user data, which contain
  // many user additions for different people, and calls getAllUsers().
  @Test public void testPublic13() {
    Twitbook twitbook= new Twitbook();
    String[] expected= new String[]{"Ada", "Addie", "Agnes", "Alice", "Alma",
                                    "Amanda", "Amelia", "Anna", "Annie",
                                    "Belle", "Bertha", "Bessie", "Blanche",
                                    "Caroline", "Carrie", "Catherine",
                                    "Charlotte", "Clara", "Cora", "Daisy",
                                    "Della", "Dora", "Edith", "Edna", "Effie",
                                    "Eliza", "Elizabeth", "Ella", "Ellen",
                                    "Elsie", "Emily", "Emma", "Ethel", "Etta",
                                    "Eva", "Fannie", "Flora", "Florence",
                                    "Frances", "Georgia", "Gertrude", "Grace",
                                    "Hannah", "Harriet", "Hattie", "Helen",
                                    "Ida", "Jane", "Jennie", "Jessie",
                                    "Josephine", "Julia", "Kate", "Katherine",
                                    "Katie", "Laura", "Lena", "Lillian",
                                    "Lillie", "Lizzie", "Lottie", "Louise",
                                    "Lucy", "Lula", "Lulu", "Lydia", "Mabel",
                                    "Mae", "Maggie", "Mamie", "Margaret",
                                    "Marie", "Martha", "Mary", "Mattie",
                                    "Maud", "Maude", "May", "Minnie",
                                    "Mollie", "Myrtle", "Nancy", "Nannie",
                                    "Nellie", "Nettie", "Nora", "Olive",
                                    "Pearl", "Rebecca", "Rosa", "Rose",
                                    "Ruth", "Sadie", "Sallie", "Sarah",
                                    "Stella", "Susan", "Susie", "Viola",
                                    "Virginia"};

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends13a.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends13b.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(), expected));
  }

  // Creates two threads, which each read a list of user data, which contain
  // many friend relationships for different people.
  @Test public void testPublic14() {
    Twitbook twitbook= new Twitbook();
    ArrayList<String> listOfUsers;
    int i= 0;

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends14a.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends14b.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(), expectedUsers));

    // in order to have the results be iterated over in order by user name-
    // to agree with the order in the static array expectedFriends above- we
    // copy whatever Collection getAllUsers() returns to an ArrayList, and
    // sort it using Collections.sort()
    listOfUsers= new ArrayList<String>(twitbook.getAllUsers());
    Collections.sort(listOfUsers);

    for (String user : listOfUsers)
      assertTrue(TestCode.checkCollection(twitbook.getFriends(user),
                                          expectedFriends[i++]));
  }

  // Creates ten threads, which each read a list of user data, which contain
  // several friend relationships for different people.
  @Test public void testPublic15() {
    Twitbook twitbook= new Twitbook();
    ArrayList<String> listOfUsers;
    int i= 0;

    twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15a.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15b.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15c.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15d.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15e.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15f.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15g.html",
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15h.html"
      });

    assertTrue(TestCode.checkCollection(twitbook.getAllUsers(), expectedUsers));

    // in order to have the results be iterated over in order by user name-
    // to agree with the order in the static array expectedFriends above- we
    // copy whatever Collection getAllUsers() returns to an ArrayList, and
    // sort it using Collections.sort()
    listOfUsers= new ArrayList<String>(twitbook.getAllUsers());
    Collections.sort(listOfUsers);

    for (String user : listOfUsers)
      assertTrue(TestCode.checkCollection(twitbook.getFriends(user),
                                          expectedFriends[i++]));
  }

}
