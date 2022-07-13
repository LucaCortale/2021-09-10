package it.polito.tdp.yelp.model;

public class Adiacenze {
	
	private Business b1;
	private Business b2;
	private String citta;
	
	public Adiacenze(Business b1, Business b2, String S) {
		
		this.b1 = b1;
		this.b2 = b2;
		this.citta = S;
	}

	public Business getB1() {
		return b1;
	}

	public void setB1(Business b1) {
		this.b1 = b1;
	}

	public Business getB2() {
		return b2;
	}

	public void setB2(Business b2) {
		this.b2 = b2;
	}

	public String getCitta() {
		return citta;
	}

	public void setPeso(String citta) {
		this.citta= citta;
	}
	
	

}
