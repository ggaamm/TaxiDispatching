package ens491;


public class main {

	/**
	 * @param args
	 */
	public static void Main() {
		// TODO Auto-generated method stub
		int citySizeX = 5;
		int citySizeY = 5;
		int taxiSize = 3;
		int custSize = 6;
		int longestTravTime = 10;
		int simulationTime = 3;
		City city = new City(taxiSize,custSize,citySizeX,citySizeY,longestTravTime);
		Dispatching dispatchingSystem = new Dispatching(city,taxiSize,custSize,citySizeX,citySizeY);
	    
	    city.GenerateTravelTimes();
	   		
		dispatchingSystem.GenerateCustomer(); //creates a customer
		
		dispatchingSystem.GenerateTaxi();
		
		dispatchingSystem.Simulate(simulationTime);


	}
}
