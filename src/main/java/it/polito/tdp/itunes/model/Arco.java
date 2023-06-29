package it.polito.tdp.itunes.model;

public class Arco implements Comparable<Arco>{
	
	Track t1;
	Track t2;
	int peso;
	
	public Arco(Track t1, Track t2, int peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = Math.abs(peso);
	}
	public Track getT1() {
		return t1;
	}
	public Track getT2() {
		return t2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Arco o) {
		return o.getPeso()-this.getPeso();
	}
	@Override
	public String toString() {
		return "Arco [" + t1 + ", " + t2 + ", peso=" + peso + "]";
	}
	
	

}
