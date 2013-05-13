package ens491;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int citySizeX = 4;
		int citySizeY = 4;
		int taxiSize = 4;
		int custSize = 6;
		int longestTravTime = 10;
		int shortestTravTime = 5;
		int simulationTime = 15;
		int randomOrLongestWaitingCust = 1; // 0 random 1 longest waiting
		int randomOrClosestWaitingTaxi = 1;
		int farPoints = 15;
		City city = new City(taxiSize, custSize, citySizeX, citySizeY,
				longestTravTime);
		int fixedOutgoingTime = 5;
		Dispatching dispatchingSystem = new Dispatching(city, taxiSize,
				custSize, citySizeX, citySizeY, randomOrLongestWaitingCust,
				randomOrClosestWaitingTaxi, farPoints, fixedOutgoingTime);

		city.GenerateTravelTimes();

		dispatchingSystem.GenerateCustomer(); // creates a customer

		dispatchingSystem.GenerateTaxi();

		dispatchingSystem.Simulate(simulationTime);
	}

}
