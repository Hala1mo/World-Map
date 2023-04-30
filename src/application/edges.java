package application;

public class edges {

	vertex d;
	vertex s;
	double weight;
	public edges(vertex s,vertex d, double weight) {
		super();
	
		this.d = d;
		this.weight = weight;
	}
	
	public vertex getD() {
		return d;
	}
	public void setD(vertex d) {
		this.d = d;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public vertex getS() {
		return s;
	}

	public void setS(vertex s) {
		this.s = s;
	}
	
	
	
	
}
