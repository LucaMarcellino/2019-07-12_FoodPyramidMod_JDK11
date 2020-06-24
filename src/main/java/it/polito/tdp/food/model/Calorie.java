package it.polito.tdp.food.model;

public class Calorie implements Comparable<Calorie> {
	private Food f;
	private Double caloria;
	public Calorie(Food f, double calorie) {
		super();
		this.f = f;
		this.caloria = calorie;
	}
	public Food getF() {
		return f;
	}
	public void setF(Food f) {
		this.f = f;
	}
	public double getCalorie() {
		return caloria;
	}
	public void setCalorie(double calorie) {
		this.caloria = calorie;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(caloria);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calorie other = (Calorie) obj;
		if (Double.doubleToLongBits(caloria) != Double.doubleToLongBits(other.caloria))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return  f.getDisplay_name() + " " + caloria;
	}
	@Override
	public int compareTo(Calorie o) {
		
		return -this.caloria.compareTo(o.getCalorie());
	}
	
	

}
