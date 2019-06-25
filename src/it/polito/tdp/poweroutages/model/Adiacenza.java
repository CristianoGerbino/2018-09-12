package it.polito.tdp.poweroutages.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private Nerc nerc1;
	private Nerc nerc2;
	private double weight;
	
	public Adiacenza(Nerc nerc1, Nerc nerc2, double weight) {
		super();
		this.nerc1 = nerc1;
		this.nerc2 = nerc2;
		this.weight = weight;
	}

	public Nerc getNerc1() {
		return nerc1;
	}

	public void setNerc1(Nerc nerc1) {
		this.nerc1 = nerc1;
	}

	public Nerc getNerc2() {
		return nerc2;
	}

	public void setNerc2(Nerc nerc2) {
		this.nerc2 = nerc2;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nerc1 == null) ? 0 : nerc1.hashCode());
		result = prime * result + ((nerc2 == null) ? 0 : nerc2.hashCode());
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
		Adiacenza other = (Adiacenza) obj;
		if (nerc1 == null) {
			if (other.nerc1 != null)
				return false;
		} else if (!nerc1.equals(other.nerc1))
			return false;
		if (nerc2 == null) {
			if (other.nerc2 != null)
				return false;
		} else if (!nerc2.equals(other.nerc2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adiacenza [nerc1=" + nerc1 + ", nerc2=" + nerc2 + ", weight=" + weight + "]";
	}

	@Override
	public int compareTo(Adiacenza o) {
		return (int) (o.getWeight()-this.getWeight());
	}
	
	
	
}
