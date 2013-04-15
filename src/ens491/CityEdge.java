package ens491;

public class CityEdge {

public String getCityFrom() {
	return cityFrom;
}

public void setCityFrom(String cityFrom) {
	this.cityFrom = cityFrom;
}

public String getCityTo() {
	return cityTo;
}

public void setCityTo(String cityTo) {
	this.cityTo = cityTo;
}

public int getCost() {
	return cost;
}

public void setCost(int cost) {
	this.cost = cost;
}

private String cityFrom;
private String cityTo;
private int cost;

}
