package ens491;

import java.util.ArrayList;

public class CityNode {

	public CityNode()
	{
		for(int i=0;i<4;i++)
		{
			String s = "-1";
			neighbors.add(s);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(ArrayList<String> neighbors) {
		this.neighbors = neighbors;
	}
	private String name;
	private ArrayList<String> neighbors = new ArrayList<String>(); //west, north, east, west
}
