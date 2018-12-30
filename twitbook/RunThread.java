package twitbook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RunThread extends Thread {
	Twitbook social;
	String url;
	//Constructor

	public RunThread(Twitbook socialNetwork) {
		// TODO Auto-generated constructor stub
		social = socialNetwork;
	}

	public void setUrl (String aUrl) {
		url = aUrl;
	}
	
	static String lock = "lock";
	
	public void run() {
		try {
			
			URL urlRead = new URL(this.url);
			URLConnection open = urlRead.openConnection();
			Scanner reader = new Scanner(open.getInputStream());
			
			String aLine = "";
			
			while (!aLine.contains("<tr>")) {
				aLine = reader.nextLine();
				}
			
				while (!aLine.contains("</table>")) {
					String person = "";
					String friend = "";
					
					if (aLine.contains("<td>")) {						
						String[] content = cutUp(aLine);
						person  = content[0];
						friend = content[1];
					}
					
					// synchronized when adding user and adding friend
					synchronized (RunThread.lock) {
						social.addUser(person);						
						social.friend(person, friend);
					}
					
					aLine = reader.nextLine();
				}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
	}
	
	//helper
	private String[] cutUp (String line) {
		String[] content = new String[2];
		
		if (line.contains("addperson")) {
			int end = line.lastIndexOf("</td>");
			String person = line.substring(28, end);
			content[0] = person;
		} 
		
		if (line.contains("addfriend")) {
			int end = line.lastIndexOf("</td>");
			line = line.substring(28, end);
			
			//cuts first friend
			end = line.indexOf("</td>");
			String friend1 = line.substring(0, end);
			
			//cuts second friend
			int start = end + 10/*"</td> <td>"= 10chars*/;
			String friend2 = line.substring(start);
			
			//stores in content
			content[0] = friend1;
			content[1] = friend2;
		}
		
		return content;
	}
	
		
	}

