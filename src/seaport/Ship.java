package seaport;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Scanner;
/**
 * File: Ship.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represents Ships on Dock. this class extends Thing.java
 */
public class Ship extends Thing{
	
	PortTime arrivalTime;
	PortTime dockTime;
	double draft;
	double length;
	double weight;
	double width;
   // int numOfJobs;

	
	/**
	 * list of jobs on ship
	 */
    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<Integer> jobstatus = new ArrayList<>();
    /**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
    public Ship(Scanner sc){
    	super(sc);
		 if (sc.hasNextDouble()) weight = sc.nextDouble();
		 if (sc.hasNextDouble()) length = sc.nextDouble();
		 if (sc.hasNextDouble()) width = sc.nextDouble();
		 if (sc.hasNextDouble()) draft = sc.nextDouble();
		
    }
    
    public int getNumofJob(){
    	return this.jobs.size();
    }
    
    @Override
    public String toString(){
    	 String st = super.toString();
    	 
    	 return st;
    }
    
    
   
    
}
    
   

