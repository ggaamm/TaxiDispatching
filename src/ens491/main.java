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
		int custSize = 8;
		int longestTravTime = 2;
		int shortestTravTime = 1;
		int simulationTime = 15;
		int randomOrLongestWaitingCust = 1; // 0 random 1 longest waiting
		int randomOrClosestWaitingTaxi = 1;
		int farPoints = 2;
		int numberOfSimulationSteps = 2;
		double AvgTaxi = 0;
		double AvgCust = 0;
		double MaxCustWaitingTime = 0;
		double MaxTaxiWaitingTime = 0;

		for(int i=0;i<numberOfSimulationSteps;i++){
		City city = new City(taxiSize, custSize, citySizeX, citySizeY,
				longestTravTime);
		int fixedOutgoingTime = 100;
		Dispatching dispatchingSystem = new Dispatching(city, taxiSize,
				custSize, citySizeX, citySizeY, randomOrLongestWaitingCust,
				randomOrClosestWaitingTaxi, farPoints, fixedOutgoingTime);

		city.GenerateTravelTimes();

		//dispatchingSystem.GenerateCustomer(); // creates a customer

		dispatchingSystem.GenerateTaxi();

		dispatchingSystem.Simulate(simulationTime);
		AvgTaxi += dispatchingSystem.getAvgTaxi();
		AvgCust += dispatchingSystem.getAvgCust();

		if(dispatchingSystem.getMaxCustWaitingTime()>MaxCustWaitingTime)
		MaxCustWaitingTime = dispatchingSystem.getMaxCustWaitingTime();
		
		if(dispatchingSystem.getMaxTaxiWaitingTime()>MaxTaxiWaitingTime)
		MaxTaxiWaitingTime = dispatchingSystem.getMaxTaxiWaitingTime();
		
		}
		
		System.out.printf("Max Customer Waiting Time: " + "%.2f\n",
		MaxCustWaitingTime);
		System.out
				.printf("Max Taxi Idle Time: " + "%.2f\n", MaxTaxiWaitingTime);
		AvgCust /= numberOfSimulationSteps;
		AvgTaxi /= numberOfSimulationSteps;
		System.out.printf("Avg. Customer Waiting Time: " + "%.2f\n", AvgCust);
		System.out.printf("Avg. Taxi Idle Time: " + "%.2f\n", AvgTaxi);
	}

}
