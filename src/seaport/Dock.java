package seaport;
import java.util.Scanner;
/**
 * File: Dock.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represents Dock on Seaport. this class extends Thing.java
 */
public class Dock extends Thing{
	/**
	 * Ship on dock
	 */
	Ship ship;
	boolean checked = false;	
	/**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
	public Dock (Scanner sc){
		super (sc);
	}
	
	@Override
	public String toString(){
		 String st = "\nDock: " + super.toString();
		 st  += "\n Ship: " + ship;
		return st;
		
	}
	
	

	

}


