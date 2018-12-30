package tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.*;

import twitbook.Twitbook;
import static org.junit.Assert.*;

public class StudentTests {

  // write your student tests in this class
	@Test
	public void studentTest1() {
		Twitbook twitbook= new Twitbook();
		
		String lines = "<table> \n" + 
"<tr> <th>Operation</th> <th>Person</th> <th>Other person</th> </tr>\n" +
"<tr> <td>addperson</td> <td>Koala</td> </tr> \n" +
"<tr> <td>addperson</td> <td>Lion</td> </tr> \n";
		
		String line = "<tr> <td>addfriend</td> <td>Koala</td> <td>Meerkat</td> </tr>";
			
	}
	
	@Test 
	public void studentTest2() {
		Twitbook twitbook= TestCode.exampleSocialNetwork2();
		Collection<String> str = twitbook.peopleYouMayKnow("Rhinoceros");

	    assertTrue(TestCode.checkCollection(twitbook.peopleYouMayKnow("Rhinoceros"),
	                                        new String[]{"Koala", "Numbat"}));
	}
	
	//calls readFriendData more than 3 times
	// 1 - creates new class, threads
	// 2 - creates new class, threads - same info
	// assert if 1 and 2 is equal
	// 3 - modifies 1. assert false to 3 and 2 being equal
	@Test 
	public void studentTest4() {
		Twitbook twitbook= new Twitbook();
		twitbook.readFriendData(new String[]{
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15a.html" ,
        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
        "friends15b.html"});
		Collection<String> users = twitbook.getAllUsers();
		
		Twitbook twitbook2= new Twitbook();
		twitbook2.readFriendData(new String[]{
		        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
		        "friends15a.html" ,
		        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
		        "friends15b.html"});
		
		twitbook2.readFriendData(new String[]{
		        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
		        "friends15a.html" ,
		        "http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
		        "friends15b.html"});

		Collection<String> users2 = twitbook2.getAllUsers();
		assertEquals(users.size(), users2.size());
		
		twitbook.readFriendData(new String[]{
				"http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
				        "friends15d.html"});
		
		twitbook.readFriendData(new String[]{
				"http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
				        "friends15e.html"});
		Collection<String> users3 = twitbook.getAllUsers();
		
		assertFalse(users2.equals(users3));
	}
	
	//calls readFriendData and calls other methods also
	@Test 
	public void studentTest5() {
		Twitbook twitbook= new Twitbook();
		twitbook.readFriendData(new String[]{
				"http://www.cs.umd.edu/class/spring2015/cmsc132-020x030x040x/" +
				        "friends15d.html"});
		twitbook.addUser("baby");
		
		assertTrue(twitbook.getAllUsers().size() == 10);
	}
	//adds empty 
	@Test 
	public void studentTest6() {
		Twitbook twitbook= new Twitbook();
		twitbook.addUser(null);
		twitbook.friend(null, "lina");

		assertTrue(twitbook.getAllUsers().isEmpty());

	}

}
