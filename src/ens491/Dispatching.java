package ens491;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import ens491.Customer.CustomerStatus;
import ens491.Taxi.TaxiStatus;

public class Dispatching {

	Random randomGenerator = new Random();
	Map<String, Integer> customerWaitingTime = new HashMap<String, Integer>();

	City city;
	private int cityDimensionX;
	private int cityDimensionY;
	private int customerSize;
	private int taxiSize;
	private static int index = 1;
	// private Graph<CityNode, DefaultWeightedEdge> cityGraph;
	private int totalPoints;
	private int simulationStep;
	private int currentSimulationTime;
	private int simType;

	// private HashMap<String,Integer> CustomerWaitingHash = new
	// HashMap<String,Integer>();

	public Dispatching(City city, int taxiSize, int customerSize,
			int citySizeX, int citySizeY) {
		// TODO Auto-generated constructor stub
		this.cityDimensionX = citySizeX;
		this.cityDimensionY = citySizeY;
		this.customerSize = customerSize;
		this.taxiSize = taxiSize;
		this.city = city;
		this.totalPoints = cityDimensionX * cityDimensionY;
	}

	public void Simulate(int timeSimulation) {
		simulationStep = timeSimulation;
		for (int i = 0; i < timeSimulation; i++) {
			currentSimulationTime = i;
			System.out.println("Simulation step:" + i);
			// generate customer
			GenerateNewCustomer(i);
			// assign customers to taxis;
			AssignCustomerToTaxi(0);
			//
			MoveTaxi();
			// Update travel times and statistics
			UpdateStatisticsAndTime();
		}
	}

	private void MoveTaxi() {
		// TODO Auto-generated method stub
		List<Taxi> taxis = city.getTaxis();
		for(Taxi t:taxis)
		{
			if(t.getTaxiStatus()==TaxiStatus.OccupiedTravellingWOCustomer)
			{
				if(t.getJourneyEndTime() ==currentSimulationTime)
				{
					t.getAssignedCust().setCustomerStatus(CustomerStatus.InTaxi);
					t.setTaxiStatus(TaxiStatus.OccupiedTravellingWCustomer);
					int endTime = (int) GetPathLengthBetweenTwoPoints(t.getAssignedCust().getStartingNode(),t.getAssignedCust().getEndingNode())+currentSimulationTime;
					t.setJourneyStartTime(currentSimulationTime);
					t.setJourneyEndTime(endTime);
					t.setCurrentPoint(t.getAssignedCust().getStartingNode());
					System.out.println("Taxi " + t.getIndex()
						+ " picked up customer " + t.getAssignedCust().getIndex() + " at current time "+currentSimulationTime+" and will end travel at:"+t.getJourneyEndTime());
				}
			}
		}
	}

	private void GenerateNewCustomer(int currentTime) {
		// TODO Auto-generated method stub
		if (randomGenerator.nextInt(3) > 0) // generate customer
		{
			if (city.getCustomers().size() < customerSize)
				GenerateCustomer();
		}
	}

	private void AssignCustomerToTaxi(int type) {
		// TODO Auto-generated method stub
		if (simType == 0)
			RandomVacantCustomer(0);
		else if (simType == 1)
			VacantCustomerWithLongestWaitingTime();
	}

	private Customer VacantCustomerWithLongestWaitingTime() {
		/*
		 * List<Customer> custs = city.getCustomers();
		 * randomGenerator.nextInt(custs.size()); for(Customer c:custs) {
		 * if(c.getCustomerStatus() == CustomerStatus.SearchingTaxi) {
		 * if(randomGenerator.nextInt(2)>0) //generate taxi {
		 * //FindVacantTaxiAndAssignCustomer(c); } } }
		 */
		return new Customer();
	}

	private void RandomVacantCustomer(int isRandom) {
		if (isRandom == 0) {
			List<Customer> custs = city.getCustomers();
			for (int i = 0; i < custs.size(); i++) {
				Customer c = custs.get(randomGenerator.nextInt(custs.size()));
				if (c.getCustomerStatus() == CustomerStatus.SearchingTaxi) {
					FindRandomVacantTaxiAndAssignCustomer(c);
					break;
				}
			}
		} 
	}

	private void FindClosestVacantTaxiAndAssignCustomer(Customer c) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Taxi> taxis = city.getTaxis();
		HashMap<String, Double> taxiDistance = new HashMap<String, Double>();
		for (Taxi t : taxis) {
			DijkstraShortestPath<String, DefaultWeightedEdge> path = new DijkstraShortestPath<String, DefaultWeightedEdge>(
					city.getCityGraph(), c.getStartingNode(),
					t.getCurrentPoint());
			taxiDistance.put(t.getCurrentPoint(), path.getPathLength());
			// path.get
		}
	}

	private void FindRandomVacantTaxiAndAssignCustomer(Customer c) {
		List<Taxi> taxis = city.getTaxis();
		for(int i=0;i<taxis.size();i++) {
			Taxi t = taxis.get(randomGenerator.nextInt(taxis.size()));
			// select the shortest taxi distance
			if (t.getTaxiStatus() == TaxiStatus.Vacant) {
				t.setAssignedCust(c);
				c.setAssignedTaxi(t);
				System.out.println("Customer " + c.getIndex()
						+ " is assigned to Taxi " + t.index);
				if (t.getCurrentPoint().equals(c.getStartingNode())) {
					t.setTaxiStatus(TaxiStatus.OccupiedTravellingWCustomer);
					c.setCustomerStatus(CustomerStatus.InTaxi);
					System.out.println("Customer " + c.getIndex()
							+ " is travelling in Taxi " + t.index
							+ " at time: " + simulationStep);
					t.setJourneyStartTime(currentSimulationTime);
					int endTime = (int) GetPathLengthBetweenTwoPoints(
							t.getCurrentPoint(), c.getEndingNode())+currentSimulationTime;
					t.setJourneyEndTime(endTime);
					System.out.println("Taxi " + t.getIndex()
							+ " reaching customer at end time: ");
				} else {
					t.setTaxiStatus(TaxiStatus.OccupiedTravellingWOCustomer);
					c.setCustomerStatus(CustomerStatus.AwaitingForTaxi);
					System.out.println("Customer " + c.getIndex()
							+ " is awaiting for Taxi " + t.index + " at time: "
							+ currentSimulationTime);
					t.setJourneyStartTime(currentSimulationTime);
					int endTime = (int) GetPathLengthBetweenTwoPoints(
							t.getCurrentPoint(), c.getStartingNode())+currentSimulationTime;
					t.setJourneyEndTime(endTime);
					System.out
							.println("Taxi " + t.getIndex()+ " reaching destination at end time: "
									+ endTime);
				}
				break;
			}
		}
	}

	public double GetPathLengthBetweenTwoPoints(String v1, String v2) {
		DijkstraShortestPath<String, DefaultWeightedEdge> path = new DijkstraShortestPath<String, DefaultWeightedEdge>(
				city.getCityGraph(), v1, v2);
		return path.getPathLength();
	}

	public void GenerateCustomer() {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		Integer randGen = randomGenerator.nextInt(totalPoints);
		c.setStartingNode(randGen.toString());
		c.setIndex(index);
		int startNode;
		do{
		randGen = randomGenerator.nextInt(totalPoints);
		startNode = Integer.parseInt(c.getStartingNode());
		}while(randGen == startNode);
		c.setEndingNode(randGen.toString());
		index++;
		c.setCustomerStatus(CustomerStatus.SearchingTaxi);
		city.AddCustomer(c);
		System.out.println("Customer " + c.getIndex()
				+ " is generated. Current point: " + c.getStartingNode()+" customer end point: "+c.getEndingNode());
	}

	public void GenerateTaxi() {
		// taxi generation
		for (int i = 0; i < taxiSize; i++) {
			Taxi c = new Taxi();
			Integer randGen = randomGenerator.nextInt(totalPoints);
			c.setCurrentPoint(randGen.toString());
			c.index = i + 1;
			c.setTaxiStatus(TaxiStatus.Vacant);
			city.AddTaxi(c);
			System.out.println("Taxi " + c.getIndex()
					+ " is generated. Current point: " + c.getCurrentPoint());
		}
	}

	private void UpdateStatisticsAndTime() {
		// TODO Auto-generated method stub
		List<Customer> customers = city.getCustomers();
		for (Customer c : customers) {
			if (c.getCustomerStatus() != CustomerStatus.InTaxi) {
				c.setWaitingTime(c.getWaitingTime() + 1);
				System.out.println("Current waiting time for customer: "
						+ c.getIndex() + " is " + c.getWaitingTime());
			} else {
				c.setTravelTime(c.getTravelTime() + 1);
				System.out.println("Current travelling time for customer: "
						+ c.getIndex() + " is " + c.getTravelTime());
			}
		}
		WriteCustomerStatisticsToFile();
		List<Taxi> taxis = city.getTaxis();
		for (Taxi t : taxis) {
			if (t.getTaxiStatus() == TaxiStatus.Vacant) {
				t.setWaitingTime(t.getWaitingTime() + 1);
				System.out.println("Current waiting time for taxi: "
						+ t.getIndex() + " is " + t.getWaitingTime());
			} else {
				t.setTravelTime(t.getTravelTime() + 1);
				System.out.println("Current travelling time for taxi: "
						+ t.getIndex() + " is " + t.getTravelTime());
			}

		}
		WriteTaxiStatisticsToFile();
		for (Taxi t : taxis) {
			if (t.getTaxiStatus() != TaxiStatus.Vacant && t.getTaxiStatus() != TaxiStatus.OccupiedTravellingWOCustomer) {
				if (t.getJourneyEndTime() == currentSimulationTime) {
					Customer customer = t.getAssignedCust();
					t.setTaxiStatus(TaxiStatus.Vacant);
					// assign Taxi to Random Place
					//Integer randGen = randomGenerator.nextInt(totalPoints);
					t.setCurrentPoint(customer.getEndingNode()); //stays where 
					t.setJourneyStartTime(0);
					t.setJourneyEndTime(0);
					t.setTravelTime(0);
					t.setWaitingTime(0);
					t.setAssignedCust(null);
					t.setEndingPoint("");
					System.out
							.println("Taxi: "
									+ t.getIndex()
									+ " reached destination,moved to point and currently vacant at: "
									+ t.getCurrentPoint());

					for (int i = 0; i < customers.size(); i++) {
						if (customers.get(i).getIndex() == customer.getIndex()) {
							System.out.println("Customer: "
									+ customers.get(i).getIndex()
									+ " reached destination, will be removed");
							customers.remove(i);
							System.out.println("Current index: " + index);
							break;
						}
					}
				}
			}
		}
	}

	private void WriteCustomerStatisticsToFile() {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = new PrintWriter(
					"/Users/gorkeralp/Desktop/customerstatistics.txt", "UTF-8");
			List<Customer> custs = city.getCustomers();
			for (Customer c : custs) {
				if (c.getCustomerStatus() == CustomerStatus.SearchingTaxi) {
					writer.println("Customer: " + c.getIndex() + " "
							+ "waiting time:" + c.getWaitingTime()
							+ " at sim step: " + currentSimulationTime);
				} else {
					writer.println("Customer: " + c.getIndex() + " "
							+ "is assigned to taxi: "
							+ c.getAssignedTaxi().getIndex()
							+ " travelling time: " + c.getTravelTime()
							+ " waiting time:" + c.getWaitingTime()
							+ " at sim step: " + currentSimulationTime);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("writing file failed.");
		}
	}

	private void WriteTaxiStatisticsToFile() {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = new PrintWriter(
					"/Users/gorkeralp/Desktop/taxistatistics.txt", "UTF-8");
			List<Taxi> taxis = city.getTaxis();
			for (Taxi t : taxis) {
				if (t.getTaxiStatus() == TaxiStatus.Vacant) {
					writer.println("Taxi: " + t.getIndex() + " "
							+ "waiting time:" + t.getWaitingTime()
							+ " at sim step: " + currentSimulationTime);
				} else {
					writer.println("Taxi: " + t.getIndex() + " "
							+ "is assigned to customer: "
							+ t.getAssignedCust().getIndex()
							+ " travelling time: " + t.getTravelTime()
							+ " waiting time:" + t.getWaitingTime()
							+ " at sim step: " + currentSimulationTime);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("writing file failed." + e.getCause());
		}
	}

	private static Map<String, Integer> sortByComparator(
			Map<String, Integer> unsortMap, final boolean order) // false for
																	// descending
																	// // order
	{

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(
				unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
