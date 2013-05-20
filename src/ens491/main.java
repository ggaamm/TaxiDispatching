package ens491;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int citySizeX = 5;
		int citySizeY = 5;
		int taxiSize = 10;
		int custSize = 50;
		int longestTravTime = 10;
		int shortestTravTime = 5;
		int simulationTime = 20;
		int randomOrLongestWaitingCust = 1; // 0 random 1 longest waiting
		int randomOrClosestWaitingTaxi = 1;
		int farPoints = 2;
		City city = new City(taxiSize, custSize, citySizeX, citySizeY,
				longestTravTime);
		int fixedOutgoingTime = 100;
		Dispatching dispatchingSystem = new Dispatching(city, taxiSize,
				custSize, citySizeX, citySizeY, randomOrLongestWaitingCust,
				randomOrClosestWaitingTaxi, farPoints, fixedOutgoingTime);

		city.GenerateTravelTimes();

		dispatchingSystem.GenerateCustomer(); // creates a customer

		dispatchingSystem.GenerateTaxi();

		dispatchingSystem.Simulate(simulationTime);
	}

}
