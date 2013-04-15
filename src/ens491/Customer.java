package ens491;

public class Customer {

public int getIndex() {
	return index;
}
public void setIndex(int index) {
	this.index = index;
}
public Taxi getAssignedTaxi() {
	return assignedTaxi;
}
public void setAssignedTaxi(Taxi assignedTaxi) {
	this.assignedTaxi = assignedTaxi;
}
public CustomerStatus getCustomerStatus() {
	return customerStatus;
}
public void setCustomerStatus(CustomerStatus customerStatus) {
	this.customerStatus = customerStatus;
}
public String getStartingNode() {
	return startingNode;
}
public void setStartingNode(String startingNode) {
	this.startingNode = startingNode;
}
public String getEndingNode() {
	return endingNode;
}
public void setEndingNode(String endingNode) {
	this.endingNode = endingNode;
}

static public enum CustomerStatus {InTaxi, AwaitingForTaxi, SearchingTaxi}; 
private Taxi assignedTaxi;
private int index;
private CustomerStatus customerStatus;
private String startingNode;
private String endingNode;
private int waitingTime;
}
