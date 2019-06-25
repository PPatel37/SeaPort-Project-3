package seaport;
import java.util.Comparator;
/**
 * File: NameComparator.java
 * Date: April 14, 2019
 * @author Pooja Patel
 * Purpose: This class implemets Comparator interface to sort the classes by Name
 */
public class NameComparator implements Comparator<SeaPort> {

	@Override
	public int compare(SeaPort t1, SeaPort t2) {
		return t1.compareTo(t2);
	}

}
