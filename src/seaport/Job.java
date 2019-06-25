package seaport;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
/**
 * File: Job.java
 * Date: April 28, 2019
 * @author Pooja Patel
 * Purpose: This class represents the Job on Dock's Ship. this class extends Thing.java and implements the Runnable to create a Thread for each Job.
 */
public class Job extends Thing implements Runnable{
	
	Ship ship;	
	SeaPort port;
	JProgressBar pm = new JProgressBar ();
	JButton jbGo   = new JButton ("Stop");
	JButton jbKill = new JButton ("Cancel");
	boolean goFlag = true;
	boolean noKillFlag = true;
	Status status = Status.SUSPENDED;
	enum Status {RUNNING, SUSPENDED, WAITING, DONE}; 
	JPanel jobPanel ;
	JPanel pane ;
	double duration;
	boolean jobDone;
	
	/**
	 * list of skills
	 */
	ArrayList<String> requirements  = new ArrayList<>();
	/**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
	public Job(HashMap <Integer, SeaPort> hmports, Scanner sc, JPanel cv){
		super(sc);
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) {
		    String s = sc.next();
			requirements.add(s);
		}
		
		ship = getParentShip(hmports, this.parent);
		port = getParentPort(hmports, ship);
		
		jobDone = false;
		pane = cv;
		jobPanel = new JPanel(new FlowLayout());
		pm = new JProgressBar ();
	    pm.setStringPainted (true);
	    jobPanel.add (new JLabel (name , SwingConstants.CENTER));
	    jobPanel.add (pm);
	    jobPanel.add (jbGo);
	    jobPanel.add (jbKill);
	    pane.add(jobPanel);
	    jbGo.addActionListener (e -> toggleGoFlag ());
	    jbKill.addActionListener (e -> setKillFlag ());
	    this.writeReport();
	    
	}
	/**
	 * A synchronized method that checks if ship is in Que or At Dock
	 * @return true if Ship is in Que.
	 */
	public synchronized boolean shiInQue(){
		if(this.port.que.contains(this.ship)){
			return true;
		}
		else{
			return false;
		}
	
	}
	/**
	 * this method finds parent dock of job
	 * @param hmports list of ports
	 * @param x is parent index of job
	 * @return 
	 * @return dock 
	 */
	public Ship getParentShip(HashMap <Integer, SeaPort> hmports, int x){
		Ship parent = null;
		for(SeaPort port: hmports.values())
			if(port.ships.containsKey(x)){
				parent = port.ships.get(x);
				//parent.jobs.add(this);
				return parent;  
			}
			
			else {
		    if(port.docks.containsKey(x)){		
		     Dock d = port.docks.get(x);
		     parent = d.ship;
			 return parent;
		    }
			}
		return parent;
		}
	
	/**
	 * method that retuns Dock for Ship
	 * @param port is Parent Port of Job
	 * @param s is Parent ship of Job
	 * @return Parent Dock of Ship
	 */ 
	public Dock getParentDock(SeaPort port, Ship s){
		Dock d = port.docks.get(s.parent);
				return d;
		      
	}
	/**
	 * this method returns the Parent Port for Job
	 * @param hmports is a portMap
	 * @param s is a Ship
	 * @return Parent port for Job
	 */
	public SeaPort getParentPort(HashMap <Integer, SeaPort> hmports, Ship s){
		SeaPort port = null;
	    if(hmports.containsKey(s.parent)){
	    	port = hmports.get(s.parent);
	    }
	    else{
	    	for(SeaPort sp: hmports.values()){
	    		if(sp.docks.containsKey(s.parent)){
	    	        port = sp;
	    		}
	    	}
	    }
		return port;
	}
	
	/**
	 * set the flag for suspend button
	 */
	public void toggleGoFlag () {
	    goFlag = !goFlag;
	  } // end method toggleRunFlag
	  
	/**
	 * set the flag for Cancel button
	 */
	  public void setKillFlag () {
	    noKillFlag = false;
	    jbKill.setBackground (Color.red);
	  } // end setKillFlag

	 /**
	  * method to show the status of Job 
	  * @param st is status
	  */
	public  void showStatus (Status st) {
		  status = st;
		  switch (status) {
		  case RUNNING:
		  jbGo.setBackground (Color.green);
		  jbGo.setText ("Running");
		  break;
		  case SUSPENDED:
		  jbGo.setBackground (Color.yellow);
		  jbGo.setText ("Suspended");
		  break;
		  case WAITING:
		  jbGo.setBackground (Color.orange);
		  jbGo.setText ("Waiting");
		  break;
		  case DONE:
		  jbGo.setBackground (Color.red);
		  jbGo.setText ("Done");
		  break;
		  } // end switch on status
		 } // end showStatus

	  
	@Override
	public void run() {
	     long time = System.currentTimeMillis();
		 long startTime = time;
		 long stopTime = time + 1000 * (long)duration;
		 double jobTime = stopTime - time;
		 ArrayList<Boolean> jobstatusList;
		 Ship newShip;
		 Dock freeDock;
		 
		 /**
		  * This block put all the jobs to wait, that is in Ship in Queue.
		  */
		 synchronized(this.port){
			 while(this.shiInQue()){
				 try {
	                   this.showStatus(Status.WAITING);
	                   this.port.wait();
	                } catch (InterruptedException e) {
	                    System.out.println("Error: " + e);
	                }
			 }
		 }
		 
		  while (time < stopTime && noKillFlag) {
		      try {
		        Thread.sleep (100);
		      } catch (InterruptedException e) {
		    	  System.out.println("Flag 2");
		      }
		      
		      if (goFlag) {
		        showStatus (Status.RUNNING);
		        time += 100;
		        pm.setValue ((int)(((time - startTime) / jobTime) * 100));
		        
		      }
		      else {
		        showStatus (Status.SUSPENDED);
		      } // end if stepping
		    } // end runninig
            
		  if(goFlag){
		    pm.setValue (100);
		    showStatus (Status.DONE);
		    this.jobDone = true;
		  }
		 
		  
		  /**
		   * This block checks if the all the job for ship is done or not. if done then it allows Ship from que to Dock.
		   */
		    synchronized(this.port){
		    	jobstatusList = new ArrayList<>();
		    	this.ship.jobs.forEach((job) -> {jobstatusList.add(job.jobStatus());});
		    	
		    	if(!jobstatusList.contains(false) && !this.getParentDock(this.port, this.ship).checked){
		           this.getParentDock(this.port, this.ship).checked = true;
		           this.writeReport().append( "\nDock: " + this.getParentDock(this.port, this.ship).name+ ":");
		    		this.writeReport().append("\nAll the Jobs for Ship " + this.ship.name + " is done on Dock " + 
		    	    this.getParentDock(this.port, this.ship).name +"\nA Ship " + this.ship.name
		    				+ " is ready to DEPARTURE\n");
		    		
		    		while(!this.port.que.isEmpty()){
		    			newShip = port.que.remove(0);
		    			if(!newShip.jobs.isEmpty()){
		    				freeDock = this.getParentDock(this.port, this.ship);
		    				freeDock.ship = newShip;
		    				newShip.parent = freeDock.index;
		    				
		    				this.writeReport().append("Ship " + newShip.name + " is moving to Dock " + freeDock.name + " from Que\n");
		  
		    				break;
		    			}
		    		}
		    		
		    	}
		    	this.port.notifyAll();
		    }
		    
	}
		 
	/**
	 * set the boolean for jobstatus	
	 * @return true if job is done
	 */
	public boolean jobStatus(){
		return this.jobDone;
	}
	
	/**
	 * method access textarea
	 * @return textarea to write report.
	 */
	public JTextArea writeReport(){
	 JScrollPane scrollpane = (JScrollPane) this.pane.getComponent(0);
	 JViewport viewport = scrollpane.getViewport(); 
	 JTextArea ta = (JTextArea)viewport.getView(); 
	return ta;
		
	}
	
	
	@Override
	public String toString(){
		String st = "Jobs: " + super.toString() + " " + duration + requirements.toString() ;
		return st;
		
	}
	
}

