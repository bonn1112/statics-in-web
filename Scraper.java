package YosukeAkutsuPP04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

public class Scraper {
	
	private Matcher matcher;
	private Regex regex;
	private String url;
	private String display = "";
	
	// constructor
	public Scraper (String url) {
		this.url = url;		
	} 
	
	// reads the data from a web page and searches for the string matches
	public void parseData() {
		String next_url = this.url;
		do {
			next_url = readNextPage(next_url);
		} 
		while (next_url != "");
	}
	
	private String readNextPage(String urlName)	{
		String next_url = "";
		StringBuilder content = new StringBuilder();

		try {
			// create a url object
			URL url = new URL(urlName);

			// create a urlconnection object
			URLConnection urlConnection = url.openConnection();
			// wrap the urlconnection in a bufferedreader
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line;

			// read from the urlconnection via the bufferedreader
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			
			bufferedReader.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		Pattern pattern1 = Pattern.compile("<tr class=\"([\\s\\S]*?)</tr>");		
		Pattern pattern2 = Pattern.compile("<td.*?</td>");
		Pattern pattern3 = Pattern.compile("</strong>.*?<a href=\"/players.*?</a>");
		Matcher matcher1 = pattern1.matcher(content);
		
		display = display + String.format("%-10s %-20s %-40s %-20s %-20s %-20s %-20s %-20s","Pos", "Num", "Player Name", "Status", "Tckl", "Sck", "Int", "Team") + "\r\n";
		
		// Loop every record
		while (matcher1.find()) {
			String player = matcher1.group(0);
			// System.out.println(record);
			Matcher matcher2 = pattern2.matcher(player);
			List<String> fields = new ArrayList<String>();
			
			while (matcher2.find()) {
				String field = matcher2.group(0).replaceAll("</td>", "").replaceAll("</a>", "").trim();
				field = field.substring(field.indexOf(">") + 1);
				field = field.substring(field.indexOf(">") + 1);
				fields.add(field);
			}
			
			display = display + String.format("%-10s %-20s %-40s %-20s %-20s %-20s %-20s %-20s", fields.get(0), fields.get(1),fields.get(2), fields.get(3), fields.get(4), fields.get(5), fields.get(7), fields.get(8)) + "\r\n";
			
		}

		Matcher matcher3 = pattern3.matcher(content);
		
		if (matcher3.find()) {
			String url_html = matcher3.group(0);
			url_html = url_html.substring(url_html.indexOf("\"") + 1);
			url_html = url_html.substring(0, url_html.indexOf("\""));
			next_url = "http://www.nfl.com" + url_html.replace("&amp;", "&");
		}

		return next_url;
		
	}
	
	// shows the output (scraped data) in a text-area 
	public String display() throws FileNotFoundException {
		
		java.io.File outputFile = new java.io.File("NFLStat.txt");
		
		// create a file
		java.io.PrintWriter output = new java.io.PrintWriter(outputFile);
		output.print(display);
		
		// close the file
		output.close();		
		return display;
		
	}

} //end class
		
		
			

	

	
	

