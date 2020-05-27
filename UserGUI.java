package YosukeAkutsuPP04;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class UserGUI extends JFrame {
	
	
	  private JButton btnScrape;
	  private JButton btnClose;
	  // add more UI components as needed
	  private Scraper scraper;
	  private JScrollPane jScrollPane;
	  private JTextArea  textArea;
	  private JLabel  lblOutput;	
	  private String url;
	  
	  public UserGUI() {
		 
		// uses the url provided in the document
		url = "http://www.nfl.com/players/search?category=position&filter=defensiveback&conferenceAbbr=null&playerType=current&conference=ALL";
		scraper = new Scraper(url);		 

	    initGUI();
	    doTheLayout();

	    btnScrape.addActionListener( new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e){
	            scrape();
	            }
	    });
	    
	    btnClose.addActionListener( new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e){
	            close();
	            }
	    });	    
	   
	  } // end of constructor
	  
	  // Creates and initialize the GUI objects
	  private void initGUI(){
		  btnScrape = new javax.swing.JButton();		  
		  btnClose = new javax.swing.JButton();
		
		  textArea = new javax.swing.JTextArea();
		  lblOutput = new javax.swing.JLabel();
		  btnScrape.setText("Scrape NFL Page");
		  btnClose.setText("Close");
		  lblOutput.setText("Output:");
		  
		  jScrollPane = new javax.swing.JScrollPane(textArea);
	  
	  }// end of creating objects method

	
	  // Layouts the UI components as shown in the project document
	  private void doTheLayout(){
		  this.setPreferredSize(new Dimension(700, 400));  
		  JPanel top = new JPanel();
		  JPanel center = new JPanel();
		  JPanel bottom = new JPanel();		  
		  
		  top.add(btnScrape);		  
		  top.add(lblOutput);
		  
		  center.setLayout(new BorderLayout());		  
		  center.add(jScrollPane);
		  
		  bottom.add(btnClose);
		  
		  setLayout(new BorderLayout());
	      add(top, "North");
	      add(center, "Center");
	      add(bottom, "South");
	  }// end of Layout method

	 
	// Uses the Scraper object reference to return and display the data as shown in the project document 
	 void scrape(){
		 scraper.parseData();
		 
		 try {
			textArea.setText(scraper.display());
		 } 
		 catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
	  }// end of scrape action event method
	  
	 
	  void close(){
	      System.exit(0);
	  }// end of close action event method


	public static void main(String[] args) {
		JFrame f = new UserGUI();
		f.setTitle("NFL Stats");
		f.setLocation(150,75);
		f.setResizable(true);  
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		
	}// end of main method

}// end of class
