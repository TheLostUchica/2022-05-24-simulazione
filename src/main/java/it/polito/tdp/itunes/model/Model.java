package it.polito.tdp.itunes.model;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao;
	Graph<Track, DefaultWeightedEdge> grafo;
	List<Arco> archi;
	
	public Model() {
		dao = new ItunesDAO();
	}
	
	public void creaGrafo(Genre g) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getTracks(g));
		archi = new LinkedList<>(dao.getArchi(g));
		for(Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getT1(), a.getT2(), a.getPeso());
		}
	}
	
	public List<Genre> setCombo1(){
		return dao.getAllGenres();
	}
	
	public List<Arco> getMax(){
		List<Arco> result = new LinkedList<>();
		Collections.sort(archi);
		Arco aa = archi.get(0);
		result.add(aa);
		
		for(Arco a : archi) {
			if(a.getPeso()==aa.getPeso() && !a.equals(aa)) {
				result.add(a);
			}
		}
		return result;
	}

	public Graph<Track, DefaultWeightedEdge> getGrafo() {
		return this.grafo;
	}
	
	public Set<Track> ComponenteConnessa(Track t) {
		ConnectivityInspector<Track, DefaultWeightedEdge> iter = new ConnectivityInspector<>(grafo);
		return iter.connectedSetOf(t);
	}
	
	
	
	LinkedList<Track> best;
	int Memoria;
	int n;
	Track tr;
	Set<Track> componente;
	
	
	public void ricorsione(Track t, int M) {
		Memoria = M;
		tr = t;
		LinkedList<Track> parziale = new LinkedList<>();
		parziale.add(t);
		componente = this.ComponenteConnessa(t);
		n = 0;
		cerca(parziale, t.getBytes());
	}
	
	private void cerca(LinkedList<Track> parziale, int memoria) {
		
		if(memoria<Memoria) {
			if(parziale.size()>n) {
				best = new LinkedList<>(parziale);
				n = parziale.size();
			}
		}else {
			return;
		}
		
		for(Track t : this.componente) {
			if(!parziale.contains(t)) {
				parziale.add(t);
				memoria +=  t.getBytes();
				
				cerca(parziale, memoria);
				
				memoria -= t.getBytes();
				parziale.remove(t);
			}
		}
	}
	
	public List<Track> getBest(){
		return best;
	}
}
