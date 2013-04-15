package ens491;

public class Taxi {

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public Customer getAssignedCust() {
		return assignedCust;
	}
	public void setAssignedCust(Customer assignedCust) {
		this.assignedCust = assignedCust;
	}
	public TaxiStatus getTaxiStatus() {
		return taxiStatus;
	}
	public void setTaxiStatus(TaxiStatus taxiStatus) {
		this.taxiStatus = taxiStatus;
	}
	
	public String getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(String currentPoint) {
		this.currentPoint = currentPoint;
	}
	public String getEndingPoint() {
		return endingPoint;
	}
	public void setEndingPoint(String endingPoint) {
		this.endingPoint = endingPoint;
	}
	
	public int getJourneyStartTime() {
		return journeyStartTime;
	}
	public void setJourneyStartTime(int journeyStartTime) {
		this.journeyStartTime = journeyStartTime;
	}
	public int getJourneyEndTime() {
		return journeyEndTime;
	}
	public void setJourneyEndTime(int journeyEndTime) {
		this.journeyEndTime = journeyEndTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	public static enum TaxiStatus {OccupiedTravellingWOCustomer, Vacant, OccupiedTravellingWCustomer};
	Customer assignedCust;
	int index;
	private TaxiStatus taxiStatus;
	private String currentPoint;
	private String endingPoint;
	private int journeyStartTime;
	private int journeyEndTime;
	private int waitingTime;
	private int travelTime;
}
