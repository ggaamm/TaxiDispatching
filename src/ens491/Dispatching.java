package ens491;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import ens491.Customer.CustomerStatus;
import ens491.Taxi.TaxiStatus;

public class Dispatching {

    Random randomGenerator = new Random();
    Map<Integer,Integer> customerWaitingTime = new HashMap<Integer,Integer>();

	City city;
	private int cityDimensionX;
	private int cityDimensionY;
	private int customerSize;
	private int taxiSize;
	private static int index=1;
	private Graph<CityNode, DefaultWeightedEdge> cityGraph;
	private int totalPoints;
	private int simulationStep;
	
	public Dispatching(City city,int taxiSize, int customerSize,int citySizeX,int citySizeY) {
		// TODO Auto-generated constructor stub
		this.cityDimensionX = citySizeX;
		this.cityDimensionY = citySizeY;
		this.customerSize = customerSize;
		this.taxiSize = taxiSize;
		this.city = city;
		this.totalPoints = cityDimensionX*cityDimensionY;
	}

	public void Simulate(int timeSimulation)
	{
		simulationStep = timeSimulation;
		for(int i=0;i<timeSimulation;i++)
		{
			System.out.println("Simulation step:"+(i+1));
			//generate customer
			GenerateNewCustomer(i);
			//assign customers to taxis;
			AssignCustomerToTaxi();
			//Update Taxi Movements
			Move();
			//Update travel times and statistics
			UpdateStatisticsAndTime();
		}
	}	

	private void Move() {
		// TODO Auto-generated method stub
		
	}
	
	private void GenerateNewCustomer(int currentTime) {
		// TODO Auto-generated method stub
		if(randomGenerator.nextInt(2)>0) //generate customer
		{
			if(city.getCustomers().size()<customerSize)
			GenerateCustomer();
			System.out.println("Generated Customer at time step: "+(currentTime+1));
		}
	}

	private void AssignCustomerToTaxi() {
		// TODO Auto-generated method stub
		RandomVacantCustomer();
	}
	
	private Customer VacantCustomerWithLongestWaitingTime()
	{/*
		List<Customer> custs = city.getCustomers();
		randomGenerator.nextInt(custs.size());
		for(Customer c:custs)
		{
			if(c.getCustomerStatus() == CustomerStatus.SearchingTaxi)
			{
				if(randomGenerator.nextInt(2)>0) //generate taxi
				{
					//FindVacantTaxiAndAssignCustomer(c);
				}
			}
		}*/
		return new Customer();
	}

	private void RandomVacantCustomer()
	{
		List<Customer> custs = city.getCustomers();
		while(true)
		{	Customer c = custs.get(randomGenerator.nextInt(custs.size()));
			if(c.getCustomerStatus()==CustomerStatus.SearchingTaxi)
			{
				FindRandomVacantTaxiAndAssignCustomer(c);
				break;
			}
		}
	}
	
	private void FindClosestVacantTaxiAndAssignCustomer(Customer c) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Taxi> taxis = city.getTaxis();
		HashMap<String, Double> taxiDistance = new HashMap<String, Double>();
		for(Taxi t:taxis)
		{
			DijkstraShortestPath<String, DefaultWeightedEdge> path = 
					new DijkstraShortestPath<String, DefaultWeightedEdge>(city.getCityGraph(),c.getStartingNode(),t.getCurrentPoint());
			taxiDistance.put(t.getCurrentPoint(), path.getPathLength());
			//path.get
		}
	}
	
	private void FindRandomVacantTaxiAndAssignCustomer(Customer c) {
		List<Taxi> taxis = city.getTaxis();
		while(true)
		{	Taxi t = taxis.get(randomGenerator.nextInt(taxis.size()));
		//select the shortest taxi distance
		if(t.getTaxiStatus() == TaxiStatus.Vacant)
		{
			t.setAssignedCust(c);
			c.setAssignedTaxi(t);
			System.out.println("Customer "+c.getIndex()+" is assigned to Taxi "+t.index);
			if(t.getCurrentPoint().equals(c.getStartingNode()))
			{
				t.setTaxiStatus(TaxiStatus.OccupiedTravellingWCustomer);
				c.setCustomerStatus(CustomerStatus.InTaxi);
				System.out.println("Customer "+c.getIndex()+" is travelling in Taxi "+t.index + " at time: "+simulationStep);
				

			}
			else
			{
				t.setTaxiStatus(TaxiStatus.OccupiedTravellingWOCustomer);
				c.setCustomerStatus(CustomerStatus.AwaitingForTaxi);
				System.out.println("Customer "+c.getIndex()+" is awaiting in Taxi "+t.index + " at time: "+simulationStep);
			}
			
			break;
		}
		}
	}

	public void GenerateCustomer() {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		Integer randGen = randomGenerator.nextInt(totalPoints);
		c.setStartingNode(randGen.toString());
		c.setIndex(index);
		randGen = randomGenerator.nextInt(totalPoints);
		c.setEndingNode(randGen.toString());
		index++;
		c.setCustomerStatus(CustomerStatus.SearchingTaxi);
		city.AddCustomer(c);
	}


	public void GenerateTaxi() {
		//taxi generation
		for(int i=0;i<taxiSize;i++)
		{
			Taxi c = new Taxi();
			Integer randGen = randomGenerator.nextInt(cityDimensionX);
			c.setCurrentPoint(randGen.toString());
			c.index = i+1;
			c.setTaxiStatus(TaxiStatus.Vacant);
			city.AddTaxi(c);
		}		
	}
	

	private void UpdateStatisticsAndTime() {
		// TODO Auto-generated method stub
		
	}
	
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) //false for descending order
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
}
