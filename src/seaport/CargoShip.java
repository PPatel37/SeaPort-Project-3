package seaport;

import java.util.Scanner;
/**
 * File: CargoShip.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represents cargo Ships on Dock. this class extends Thing.java
 */
public class CargoShip extends Ship{
  
	double cargoValue;
	double cargoVolume; 
	double cargoWeight; 
	/**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
	public CargoShip (Scanner sc){
		super (sc);
		 if (sc.hasNextInt()) cargoValue = sc.nextInt();
		 if (sc.hasNextInt()) cargoVolume = sc.nextInt();
		 if (sc.hasNextInt()) cargoWeight = sc.nextInt();
	}//end constructor
	
	@Override
	 public String toString () {
		 String st = "Cargo ship: " + super.toString();
		 if (jobs.size() == 0){
			// System.out.println("Flag 1");
		 return st;}
		 else{ for (Job mj: jobs) st += "\n - " + mj;
	//	 System.out.println("Flag 2");
		 return st;
		 }
		 } 
}

