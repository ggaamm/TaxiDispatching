package ens491;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int citySizeX = 4;
		int citySizeY = 4;
		int taxiSize = 3;
		int custSize = 5;
		int longestTravTime = 2;
		int simulationTime = 10;
		City city = new City(taxiSize, custSize, citySizeX, citySizeY,
				longestTravTime);
		Dispatching dispatchingSystem = new Dispatching(city, taxiSize,
				custSize, citySizeX, citySizeY);

		city.GenerateTravelTimes();

		dispatchingSystem.GenerateCustomer(); // creates a customer
		
		dispatchingSystem.GenerateCustomer(); // creates a customer


		dispatchingSystem.GenerateTaxi();

		dispatchingSystem.Simulate(simulationTime);
	}

}
