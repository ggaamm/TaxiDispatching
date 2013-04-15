package ens491;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.graph.*;
import ens491.CityNode;
import ens491.CityEdge;

public class City {

	public City(int fleetSize, int customerSize, int cityDimensionX, int cityDimensionY, int maxTravelTime)
	{
		customers = new ArrayList<Customer>(customerSize);
		taxis = new ArrayList<Taxi>(fleetSize);
		this.cityXDimension = cityDimensionX;
		this.cityYDimension = cityDimensionY;
		travelTimes = new int[cityXDimension][cityYDimension];
		totalPoints = cityDimensionX * cityDimensionY;
		maxTravTime = maxTravelTime;
		cityGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
Random randomGenerator = new Random();
private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> cityGraph; 

public int getCityXDimension() {
	return cityXDimension;
}
public void setCityXDimension(int cityXDimension) {
	this.cityXDimension = cityXDimension;
}
public int getCityYDimension() {
	return cityYDimension;
}
public void setCityYDimension(int cityYDimension) {
	this.cityYDimension = cityYDimension;
}

public List<Customer> getCustomers() {
	return customers;
}
public void setCustomers(List<Customer> customers) {
	this.customers = customers;
}
public List<Taxi> getTaxis() {
	return taxis;
}
public void setTaxis(List<Taxi> taxis) {
	this.taxis = taxis;
}

public void AddCustomer(Customer cust)
{
	customers.add(cust);
}

public void AddTaxi(Taxi taxi)
{
	taxis.add(taxi);
}

public void GenerateTravelTimes()
{
	for(int i=0;i<totalPoints;i++)
	{
		CityNode cn = new CityNode();
		cn.setName(new Integer(i).toString());
		nodes.add(cn);
		String intt = new Integer(i).toString();
		cityGraph.addVertex(intt);
		System.out.println(cityGraph.toString());
	}
	GenerateEdges();
}
private void GenerateEdges() { //Generating 2D Mesh
	// TODO Auto-generated method stub
	for(int i=0;i<cityYDimension;i++)
	{
		for(int j=0;j<cityXDimension;j++)
		{
			int t = i*cityXDimension + j;
			CityNode cn = nodes.get(t);
			int rightoft = t+1;
			if(rightoft%cityXDimension !=0){
				CityNode cnR = nodes.get(rightoft);
				cn.getNeighbors().set(2, cnR.getName()); //add to east
				CityEdge ce = new CityEdge();
				ce.setCityFrom(cn.getName());
				ce.setCityTo(cnR.getName());
				ce.setCost(randomGenerator.nextInt(maxTravTime));
				edges.add(ce);
				
				cnR.getNeighbors().set(0, cn.getName()); //add to west
				ce = new CityEdge();
				ce.setCityFrom(cnR.getName());
				ce.setCityTo(cn.getName());
				ce.setCost(randomGenerator.nextInt(maxTravTime));
				edges.add(ce);
				
			}
			int upoft = t+cityXDimension;
			if(upoft<totalPoints){
				CityNode cnU = nodes.get(upoft);
				cn.getNeighbors().set(1, cnU.getName()); //add to north
				CityEdge ce = new CityEdge();
				ce.setCityFrom(cn.getName());
				ce.setCityTo(cnU.getName());
				ce.setCost(randomGenerator.nextInt(maxTravTime)+1);
				edges.add(ce);
				
				cnU.getNeighbors().set(3, cn.getName()); //add to south
				ce = new CityEdge();
				ce.setCityFrom(cnU.getName());
				ce.setCityTo(cn.getName());
				ce.setCost(randomGenerator.nextInt(maxTravTime));
				edges.add(ce);
			}
		}
	}
	for(int i=0;i<edges.size();i++)
	{
		CityEdge ce = edges.get(i);
		DefaultWeightedEdge e1 = cityGraph.addEdge(ce.getCityFrom(), ce.getCityTo()); 
        cityGraph.setEdgeWeight(e1, ce.getCost()); 
		System.out.println("ce: "+ce.getCityFrom()+" "+ce.getCityTo()+" "+ce.getCost());
	}
	System.out.println(cityGraph.toString());
}
public ArrayList<CityEdge> getEdges() {
	return edges;
}
public void setEdges(ArrayList<CityEdge> edges) {
	this.edges = edges;
}
public ArrayList<CityNode> getNodes() {
	return nodes;
}
public void setNodes(ArrayList<CityNode> nodes) {
	this.nodes = nodes;
}
public int getMaxTravTime() {
	return maxTravTime;
}
public void setMaxTravTime(int maxTravTime) {
	this.maxTravTime = maxTravTime;
}
public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getCityGraph() {
	return cityGraph;
}
public void setCityGraph(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph) {
	this.cityGraph = simpleDirectedWeightedGraph;
}
ArrayList<CityEdge> edges = new ArrayList<CityEdge>();
ArrayList<CityNode> nodes = new ArrayList<CityNode>();
int totalPoints;
int cityXDimension;
int cityYDimension;
int[][] travelTimes;
List<Taxi> taxis;
List<Customer> customers;
int maxTravTime; 
}
