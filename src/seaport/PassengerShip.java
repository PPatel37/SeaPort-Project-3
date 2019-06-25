package seaport;

import java.util.Scanner;
/**
 * File: Passenger Ship.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represents Passenger Ships on Dock. this class extends Thing.java
 */
public class PassengerShip extends Ship{
	
	int numberOfOccupiedRooms;
	int numberOfPassengers;
	int numberOfRooms;
	/**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
	public PassengerShip (Scanner sc) {
		 super (sc);
		 if (sc.hasNextInt()) numberOfPassengers = sc.nextInt();
		 if (sc.hasNextInt()) numberOfRooms = sc.nextInt();
		 if (sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
		 } // end end Scanner constructor

	@Override
	 public String toString () {
		 String st = "Passenger ship: " + super.toString();
		 if (jobs.size() == 0)
		 return st;
		 for (Job mj: jobs) st += "\n - " + mj;
		 return st;
		 } 

}