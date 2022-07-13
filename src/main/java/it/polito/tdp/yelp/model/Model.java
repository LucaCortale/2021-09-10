package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private Graph <Business,DefaultWeightedEdge> grafo;
	YelpDao dao;
	Map <String,Business> idMap;
	List <Business> listaB ;
	List <Review> listaR;
	List <User> listaU;
	List<Business> best;
	double km;
	
	
	public Model() {
	
	idMap = new HashMap<String , Business>();
	dao = new YelpDao();
	listaB = this.getAllBusiness();	
	
	listaU = this.getAllUser();	
	
	}
	
	public List <Business> getAllBusiness(){
		return dao.getAllBusiness();
	}
	
	public List<Review> getAllReviewsCity(String citta,Map<String,Business> idMap){
		return dao.getAllReviewsCity(citta, idMap);
	}
	
	public List <User> getAllUser(){
		return dao.getAllUsers();
	}
	
	public List <Business> getAllBusinessCity(String s){
		return dao.getAllBusinessCity(s);
	}
	
	public List <Adiacenze> getAdiacenze(Map <String,Business> idMap,String s){
		return dao.getAdiacenze(idMap, s);
	}
	
	public List<String> getAllCity(){
		List<String> lista = dao.getAllCity();
		Collections.sort(lista);
		return lista;
	}
	
	public List<Business> getAllLocaliCity(String citta){
		
		List <Business> lista = dao.getAllLocaliCity(citta);
		
		return lista;
	}
	
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public void creaGrafo(String s) {
		
		for(Business b : this.listaB)
			this.idMap.put(b.getBusinessId(), b);
		
		this.grafo = new SimpleWeightedGraph<Business,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.getAllBusinessCity(s));
		List<Adiacenze> adiacenze = new ArrayList<>();
		adiacenze = this.getAdiacenze(idMap, s);
		for(Adiacenze a : adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, a.getB1(), a.getB2(), this.calcolaDistanza(a.getB1(), a.getB2()));
		}
	}
	
	
	public double calcolaDistanza(Business b1,Business b2) {
		return LatLngTool.distance(new LatLng(b1.getLatitude(),b1.getLongitude()),
				new LatLng(b2.getLatitude(),b2.getLongitude()),
						LengthUnit.KILOMETER);
	}
	
	public String getLocaleDistante(Business b) {
		
		double pesoMax = 0;
		Business max = null;
		
		for(Business bb: this.grafo.vertexSet()) {
			
			if(this.calcolaDistanza(b, bb) > pesoMax) {
				pesoMax = this.calcolaDistanza(b, bb);
				max = bb;
			}
		}
		return max.getBusinessName()+ " è distante "+pesoMax;
	}
	
	public List<Business> calcolaPercorso(Business sorgente,Business destinazione,double x) {
		
		best = new LinkedList();
		List<Business> parziale = new LinkedList();
		parziale.add(sorgente);
		cerca(parziale,destinazione,x);
	
		
		return best;
	}

	private void cerca(List<Business> parziale, Business destinazione, double x) {
		
		//condizione terminazione
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			
		
		//è la soluzione migliore?
			if(parziale.size() > best.size()) {
				best = new LinkedList(parziale);
			}
			return;
		}
		
		for(Business b : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			
			if(b.getStars() >= x) {
				if(!parziale.contains(b)) {
					parziale.add(b);
					cerca(parziale,destinazione,x);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}
	
	
	public double contaDistanzaTotale(Business destinazione) {
		this.km =0.0;
		for(int i =0 ; i<this.best.size();i++) {
			if(!best.get(i).equals(destinazione))
			km += calcolaDistanza(best.get(i), best.get(i+1));
		}
		return km;
	}
	
}
