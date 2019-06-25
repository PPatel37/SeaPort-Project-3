package seaport;

import java.util.Scanner;
/**
 * File: Person.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represents Person. this class extends Thing.java
 */
public class Person extends Thing {
	String skill;
	/**
	 * Constructor to create an object of class
	 * @param sc is Scanner that reads input file
	 */
	public Person(Scanner sc){
		super(sc);
		if (sc.hasNext()) skill = sc.next();
	}

@Override
public String toString(){
	String st = "Person: " + super.toString() + " " + skill ;
	return st;
	
}
}
