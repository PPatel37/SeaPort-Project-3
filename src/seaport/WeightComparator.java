package seaport;

import java.util.Comparator;
/**
 * File: WeightComparator.java
 * Date: April 14, 2019
 * @author Pooja Patel
 * Purpose: This class implemets Comparator interface to sort the ships by weight
 */
public class WeightComparator implements Comparator<Ship> {

	
	@Override
	public int compare(Ship s1, Ship s2) {
		if(s1.weight < s2.weight) 
			return -1;
		else if(s1.weight > s2.weight) 
			return 1;
		else return 0;
	
	}


}

