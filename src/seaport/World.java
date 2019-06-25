package seaport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * File: World.java
 * Date: March 31, 2019
 * @author Pooja Patel
 * Purpose: This class represent the world that has one or more SeaPort. This is the subclass of Thing
 */
public class World extends Thing {

	/**
	 * HashMap of SeaPorts
	 */
	HashMap<Integer, SeaPort> ports = new HashMap<Integer,SeaPort>();
	/**
	 * HasMap for sorted map
	 */
	HashMap<Integer, SeaPort> sortedMap = new LinkedHashMap<Integer, SeaPort>();
	/**
	 * represents time
	 */
	PortTime time;
	/**
	 * this is the list that holds search things
	 */
	ArrayList<Thing> searchList ;
	
	JPanel jrun = new JPanel ();
	

	/**
	 * no-argumet constructor
	 */
	
	public World(){}
	
	
	
/**
 *  Handling a line from the file
 * @param st is a line from file 
 */
	public void process (String st) {
	 
		 Scanner sc = new Scanner(st);
		 if (!sc.hasNext()){
			 sc.close();
		     return;}
		 switch (sc.next()) {
		 case "port" : 
			 addPort (sc);
		     break;
		 case "dock":
			addDock(sc);
			 break;
		 case "pship":
			 addPship(sc);
			 break;
		 case "cship":
			 addCship(sc);
			 break;
		 case "person":
			 addPerson(sc);
			 break;
		 case "job":
			// System.out.println ("Processing >" + st + "<");
			 addJobs(sc);
			 break;

		 }
		 
		 }//end of process
	 

	/**
	 * Adding port to list 
	 * @param sc is a scanner that reads input file
	 */
   public void addPort(Scanner sc) {
	SeaPort port = new SeaPort(sc);
	ports.put(port.index, port);	 
}
 

	/**
	 * Adding dock to Hashmap 
	 * @param sc is a scanner that reads input file
	 */
   public void addDock(Scanner sc){
		 Dock dock = new Dock(sc);
		 ports.get(dock.parent).docks.put(dock.index, dock);
	}

	/**
	 * Adding passenger ship to Hashmap 
	 * @param sc is a scanner that reads input file
	 */
   public void addPship(Scanner sc){
	   Ship pShip = new PassengerShip(sc);
	   assignShip(pShip);
   }

	/**
	 * Adding cargo ship to Hashmap
	 * @param sc is a scanner that reads input file
	 */
   public void addCship(Scanner sc){
	   Ship cShip = new CargoShip(sc);
	   assignShip(cShip);
   }

	/**
	 * Adding person to hashmap
	 * @param sc is a scanner that reads input file
	 */
   public void addPerson(Scanner sc){
	   Person p = new Person(sc);
	   ports.get(p.parent).persons.put(p.index, p);
   }
   /**
    * adding jobs to ship
    * @param sc
    */
   //optional till project 3 and 4
    public void addJobs(Scanner sc){
    	//System.out.println("adding job");
    	Job j = new Job (ports, sc, jrun);
    //	System.out.println(j.parent);
        (new Thread (j)).start();
    	for(SeaPort port: ports.values()){
    		if(port.ships.containsKey(j.parent)){
    			Ship s = port.ships.get(j.parent);
    			s.jobs.add(j);
    			//System.out.println("Job added in to Ship successfully");
    			return;
    			}
    		
    		if(port.docks.containsKey(j.parent)){
    			Dock d = port.docks.get(j.parent);
    			d.ship.jobs.add(j);
    		//	System.out.println("Job added in to dock successfully");
        		return;
    		}
    			
    		
    	}
    }
   
 /**
  * Linking a ship to its parent
  * @param ms is a ship
  */
  public void assignShip (Ship ms) {
	  
	 for(SeaPort port: ports.values()){
		if(port.docks.containsKey(ms.parent)){
			Dock d = port.docks.get(ms.parent);
			d.ship=ms;
			port.ships.put(ms.index, ms);
			return;
		}
	 }
	 
			SeaPort s = ports.get(ms.parent);
			s.ships.put(ms.index, ms);
			s.que.add(ms);
		}
	 


/**
 * performs search	 
 * @param type is a type of search user perform
 * @param target is a input from textfield
 */
public void doSearch(String type,String target)	{
	searchList = new ArrayList<>();
	switch(type){
	case "Name":
		
		for(SeaPort port: ports.values())
			searchName(port,target);
		
		break;
		
	case "Index":
		int x = Integer.parseInt(target);
		 if(ports.containsKey(x)){
			 searchList.add(ports.get(x));
		 }
		 for(SeaPort port: ports.values())
	     searchIndex(port,x);
		break;
	case "Skills":
		for(SeaPort port: ports.values())
		for(Person p: port.persons.values())
			if(p.skill.equalsIgnoreCase(target)){
				searchList.add(p);
			}
		break;
	}
}


/**
 * method that search for name in each port for target		
 * @param port is a Seaport
 * @param target is a input entered by user
 * @return return a search list of things that maches with input target
 */
 public ArrayList<Thing> searchName(SeaPort port, String target){
	
	if (port.name.equalsIgnoreCase(target)){
		searchList.add(port);
		
	}
	for(Dock dock: port.docks.values())
		if(dock.name.equalsIgnoreCase(target))
			searchList.add(dock);
	for(Ship ship: port.ships.values() )
		if(ship.name.equalsIgnoreCase(target))
			searchList.add(ship);
	for(Person p: port.persons.values())
		if(p.name.equalsIgnoreCase(target))
			searchList.add(p);
	
	return searchList;
}


/**
 * method that search for index in each port for target		
 * @param port is a Seaport
 * @param target is a input entered by user
 * @return return a search list of things that maches with input target
 */
public ArrayList<Thing> searchIndex(SeaPort port, int x){
	
	    if(port.docks.containsKey(x)){
	    	Dock d = port.docks.get(x);
	    	searchList.add(d);
	    }
	    
	    if(port.ships.containsKey(x)){
	    	Ship s = port.ships.get(x);
	    	searchList.add(s);
	    }
		
	   if(port.persons.containsKey(x)){
		   Person p = port.persons.get(x);
		   searchList.add(p);
	   }
	
	return searchList;
}

/**
 * method that perform sort	
 * @param t is option chose by user
 */
public void doSort(String t){
	switch(t){
	case "Ship by Draft" :
		for (SeaPort port: ports.values())
			Collections.sort(port.que, new DraftComparator());
		break;
	case "Ship by Width" :
		for (SeaPort port: ports.values())
			Collections.sort(port.que, new WidthComparator() );
		break;
	case "Ship by Weight" :
		for (SeaPort port: ports.values())
			Collections.sort(port.que, new WeightComparator());
		break;
	case "Ship by Length" :
		for (SeaPort port: ports.values())
			Collections.sort(port.que, new LengthComparator() );
		break;
	case "Ship by Name":
		for(SeaPort port: ports.values())
			sortbyNameShip(port);
		break;
	case "SeaPort by Name":
		sortbyNameSeaPort();  
		break;
	
	case "Person by Name":
		for(SeaPort port: ports.values())
		sortbyNamePerson(port);
		break;
	}
}

/**
 * sort ports by name
 */
public void sortbyNameSeaPort(){
	
	 List < SeaPort> portlist =  new ArrayList<SeaPort>(ports.values());  
     Collections.sort(portlist);
     for(SeaPort port: portlist)
    	 
    	 sortedMap.put(port.index, port);
}

/**
 * sort Ships by Name
 * @param p is port in which ships sorted
 */
public void sortbyNameShip(SeaPort p){
	
	List <Ship> shiplist = new ArrayList<Ship>(p.ships.values());
     Collections.sort(shiplist);
    
    	 HashMap <Integer, Ship> sortedShip = new LinkedHashMap <Integer, Ship>(); 
    	 for(Ship s: shiplist)
    		 sortedShip.put(s.index, s);
    	 
    	 sortedMap.put(p.index, p);
    	 p.ships = sortedShip;  	
}    
/**
 * sort persons by name
 * @param p is port in which persons sorted
 */

public void sortbyNamePerson(SeaPort p){
	List <Person> personlist = new ArrayList<Person>(p.persons.values());
    Collections.sort(personlist);
   
   	 HashMap <Integer, Person> sortedPerson = new LinkedHashMap <Integer, Person>(); 
   	 for(Person per: personlist)
   		 sortedPerson.put(per.index, per);
   	 
   	 sortedMap.put(p.index, p);
   	 p.persons = sortedPerson;  	
}



}
