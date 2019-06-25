package seaport;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 * File: SeaPortProgram.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This is the main class that launch the GUI for SeaPort program
 */

public class SeaPortProgram extends JFrame{
	
	static final long serialVersionUID = 1L;

	World world = new World();
	
	JTextArea jta = new JTextArea();
	JComboBox <String> jcb, jcbSort;
	JTextField jtf;
	String token;
	Scanner scanLine;
	String line;
	JTree tree;
	JScrollPane pane2;
	
	
	/**
	 *No-arg constructor
	 */
	public SeaPortProgram(){
	        setTitle ("SeaPort Project");
	        setSize (800, 400);
	        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	        setVisible (true);
	        jta.setFont (new java.awt.Font ("Monospaced", 0, 12)); 
	        JTextArea jobStats = new JTextArea(10,50);
	        jobStats.setEditable(false);
	        jobStats.setFont (new java.awt.Font ("Monospaced", 0, 12));
	        JScrollPane jsp2 = new JScrollPane(jobStats);
	        jobStats.setText("Job Status:");
	        world.jrun.add(jsp2);
	        JScrollPane jsp = new JScrollPane(jta);
	        jsp.setPreferredSize(new Dimension(50,300));
	        JScrollPane jsp1 = new JScrollPane(world.jrun);
	        jsp1.setPreferredSize(new Dimension (100,300));
	        tree = new JTree(createNode("The World"));
        	pane2 = new JScrollPane(tree);
        	pane2.setPreferredSize(new Dimension(100,300));
            JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pane2,jsp);
            JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitpane,jsp1);
            add(mainSplit, BorderLayout.CENTER);	  
       
	        JLabel jlbl = new JLabel ("Search Target");
	        jtf = new JTextField (10);
	        //combo box
	        jcb = new JComboBox <String> ();
	        jcb.addItem ("Name");
	        jcb.addItem ("Index");
	        jcb.addItem ("Skills");
	       
	        //Buttons
	        JButton jbr = new JButton ("Read file");
	        JButton jbs = new JButton ("Search");
	        JButton jbd = new JButton ("Display Text");
	        JButton jbt = new JButton ("Display Tree");
	        //adding for project 2
	        jcbSort = new JComboBox <String> ();
	        JButton sort = new JButton ("Sort");
	      
	       // jcbSort.addItem("SeaPort by Name");
	        jcbSort.addItem("Person by Name");
	        jcbSort.addItem("Ship by Name");
	        jcbSort.addItem("Ship by Draft");
	        jcbSort.addItem("Ship by Width");
	        jcbSort.addItem("Ship by Length");
	        jcbSort.addItem("Ship by Weight");
	        
	        JPanel mainPane = new JPanel(new BorderLayout());
	        JPanel jp = new JPanel ();
	        JPanel jp1 = new JPanel();
	        jp.add (jbr);
	        jp.add(jbd);
	        jp.add(jbt);
	        //jp1.add (jbd);
	        jp1.add (jlbl);
	        jp1.add (jtf);
	        jp1.add (jcb);
	        jp1.add (jbs);
	        jp1.add(jcbSort);
	        jp1.add(sort);
	        mainPane.add(jp, BorderLayout.NORTH);
	        mainPane.add(jp1, BorderLayout.SOUTH);
	        world.jrun.setLayout(new BoxLayout(world.jrun, BoxLayout.Y_AXIS));
	      
	        add (mainPane, BorderLayout.PAGE_START);
	       
	        validate();
	     
	        
	        
	    jbr.addActionListener ( new ActionListener () {
                public void actionPerformed (ActionEvent e) {
                	JFileChooser jfc = new JFileChooser ("."); 
            		int returnVal = jfc.showOpenDialog(null);
            		
            	       if(returnVal == JFileChooser.APPROVE_OPTION) {
      
            	          try {
            	             Scanner sfin = new Scanner (jfc.getSelectedFile());
                             readFile (sfin);
                             
                             
            	          } 
         	             catch (FileNotFoundException ex) {
         	                JOptionPane.showMessageDialog(null, "File not found.");
         	             }
                }
            	      
                       // end required method
            }
	    }// end local definition of inner class
        ); // the anonymous inner class
      //  jbd.addActionListener(e -> display());
        jbd.addActionListener ( new ActionListener () {
                public void actionPerformed (ActionEvent e) {
                    display ();
                } // end required method
            } // end local definition of inner class
        ); // the anonymous inner class
        
        jbs.addActionListener ( new ActionListener () {
                public void actionPerformed (ActionEvent e) {
                    search ((String)(jcb.getSelectedItem()), jtf.getText());
                } // end required method
            } // end local definition of inner class
        ); // the anonymous inner class
        
        sort.addActionListener ( new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                sort ((String)(jcbSort.getSelectedItem()));
            } // end required method
        } // end local definition of inner class
    );
	    
        jbt.addActionListener(e -> displayTree());
        
	}//end constructor
	
	/**
	 * method that handles Read button
	 */
	public void readFile (Scanner sfin) {
		 // NOT the file name!!!!
	             while (sfin.hasNext()) {
	            	 
	            	  line = sfin.nextLine();
	            	 if(line.startsWith("//") || line.isEmpty()){
	            		 continue;
	            	 }
	            	
	            	 world.process(line);	
	            	
	            	 
	            	 
	             }
	             sfin.close();
	            
	         
	          
	       } 
    
    /**
     * this method handles display button
     */
    public void display () {
      
         jta.setText ("" + world.ports.values().toString());
         
    	}
    public void displayTree(){
    	
    	tree.setModel(new DefaultTreeModel(createNode("The World")));
      //	System.out.println("Display tree button clicked");
     	
    }
    
    /**
     * This method handles Search button
     * @param type is a type of search user want to perform
     * @param target is entered string in textfield
     */
    public void search (String type, String target) {
    	world.doSearch(type,target);
    	if (world.searchList.isEmpty()){
    	jta.setText( type + ": " + target + " Does not exist");
    	}else{
    	jta.setText("Search Result for: " + type + ": " + target + "\n\n"+ world.searchList.toString());  
    	
    }
    }
    /**
     * handles sorting button
     * @param sortType is a type of sort user choose from JCombobox
     */
    public void sort(String sortType){
    	String s = sortType.substring(sortType.lastIndexOf(" ")+ 1);
    	  if (s == "Name"){
          world.doSort(sortType);
          jta.setText( world.sortedMap.values().toString());
          return;
    	}
    	else{
    		 world.doSort(sortType);
    	     jta.setText( world.ports.values().toString());
    	}
    }
  //adding all for p3
    public DefaultMutableTreeNode createNode(String title){
    //	System.out.println("Flag 1 to check");
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
    	DefaultMutableTreeNode seaport = new DefaultMutableTreeNode("SeaPorts");
    	
    	
    	DefaultMutableTreeNode spc, dkc, sh, shs, pr, qship;
        
    	
    	
    	top.add(seaport);
    	
    	
        for(SeaPort port: world.ports.values()){
        	//System.out.println("Flag 2 to check");
        	spc = new DefaultMutableTreeNode(port.name);
        	DefaultMutableTreeNode docks = new DefaultMutableTreeNode("Docks");
        	DefaultMutableTreeNode que = new DefaultMutableTreeNode("Que");
        	DefaultMutableTreeNode ships = new DefaultMutableTreeNode("Ships");
        	DefaultMutableTreeNode person = new DefaultMutableTreeNode("Persons");
        	spc.add(docks);
        	spc.add(que);
        	spc.add(ships);
        	spc.add(person);
        	
        	for(Dock dock: port.docks.values()){
        		
        		dkc = new DefaultMutableTreeNode(dock.name);
        		//DefaultMutableTreeNode dship = new DefaultMutableTreeNode("Ship");
        		Ship s = dock.ship;
        		//docks.add(dship);
        		sh = new DefaultMutableTreeNode(s.name);
        		DefaultMutableTreeNode jp = new DefaultMutableTreeNode("Jobs");
        		
        		for(Job j: s.jobs){
        			DefaultMutableTreeNode jobs = new DefaultMutableTreeNode(j.name);
        			jp.add(jobs);
        		}
        		sh.add(jp);
        		dkc.add(sh);
        		docks.add(dkc);
        	}
        	
        	for(Ship ship: port.que){
        		
        		qship = new DefaultMutableTreeNode(ship.name);
        		que.add(qship);
        	}
        	
        	for(Ship ship: port.ships.values()){
        		
        		shs = new DefaultMutableTreeNode(ship.name);
        		ships.add(shs);
        	}
        	for(Person p: port.persons.values()){
        		
        		pr = new DefaultMutableTreeNode(p.name);
        		person.add(pr);
        	}
        	
        	seaport.add(spc);
        }
        
        
        return top;
    }
    
    public DefaultTreeModel createModel(){
   
    	return (new DefaultTreeModel(createNode("The World")));
    }
    
	/**
	 * main method that launch the SeaPort program
	 * @param args
	 */
	public static void main (String args []) {
		     new SeaPortProgram ();
		   
		  } 
	 
}
