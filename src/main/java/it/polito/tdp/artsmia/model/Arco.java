package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco>{

	int id1, id2;
	double peso;
	
	public Arco(int id1, int id2, double peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Arco o) {
		
		if(this.getPeso()>o.getPeso()) {
			
			return -1;
		}
		if(this.getPeso()<o.getPeso()) {
			
			return 1;
		}
		
		return 0;
	}
	
	
	
}
