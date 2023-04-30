package application;

import java.util.LinkedList;

public class vertex  {
	String name;
	 vertex previous;
	double latitude;
	double longitude;
	int num;
	double distance=Integer.MAX_VALUE;
	boolean visited;

	LinkedList<edges> e = new LinkedList<edges>();

	public vertex(String name, double latitude , double longitude, int number) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.num = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public LinkedList<edges> getE() {
		return e;
	}

	public void setE(LinkedList<edges> e) {
		this.e = e;
	}

	public boolean FindEdge(String ss) {

		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).getD().getName().compareToIgnoreCase(ss) == 0)
				return true;
		}
		return false;
	}

	public String ttoString() {
		String r = name+":";
		for (int i = 0; i < e.size(); i++) {
			r = r + e.get(i).d.name + ",";
		}
		return r;
	}

	
}
