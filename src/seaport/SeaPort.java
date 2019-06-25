package seaport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 * File: SeaPort.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class is subclass that extends super class Thing. this class represents Seaport
 */
public class SeaPort extends Thing{
	
	/**
	 * map of docks on SeaPort
	 */ 
	HashMap<Integer, Dock> docks = new HashMap<Integer, Dock>();
	/**
	 * list of ships in a que
	 */
	ArrayList<Ship> que = new ArrayList<>(); 
	/**
	 * map of ships present on port
	 */
	HashMap<Integer, Ship> ships = new HashMap<Integer, Ship>();
	/**
	 * map of person working on port
	 */
	HashMap<Integer, Person> persons = new HashMap<Integer, Person>();
	
	/**
	 * Constructer to create an object of class SeaPort
	 * @param sc is a Scanner that read data from input file
	 */
	
	public SeaPort(Scanner sc){
	  super(sc);
		
	}

	
	@Override
	public String toString () {
		 String st = "\n\n\n>>>>> The world: \n\nSeaPort: " + super.toString();
		
		 for (Dock dock: docks.values())
			 st += "\n" + dock;
		 
		 st += "\n\n --- List of all ships in que:"; 
		 for (Ship ms: que ) 
			 st+= "\n > " + ms + " "+ ms.weight + "Weight " + ms.length+ "Length " + ms.width+ "Width " + ms.draft + "Draft ";
		 
		 st += "\n\n --- List of all ships:";
		
		 for (Ship ms: ships.values())
			 st += "\n > " + ms;
		 st += "\n\n --- List of all persons:";
		
		 for (Person mp: persons.values())
			 st += "\n > " + mp;
		 return st;
		 } // end method toString

	@Override
	//compare all things by name
	public int compareTo(Thing o) {
		SeaPort t = (SeaPort) o;
		if(this.name.equals(t.name)){
			return 0;
		}
		else{
			return this.name.compareTo(t.name);
		}
	}
	
}
