package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private PowerOutagesDAO dao;
	private List<Nerc> listaNerc;
	private Map<Integer, Nerc> idMapNerc;
	
	
	public Model() {
		this.listaNerc = new ArrayList<Nerc>();
		this.idMapNerc = new HashMap<>();
		this.dao = new PowerOutagesDAO();
		
		listaNerc = dao.loadAllNercs(idMapNerc);
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungiamo i vertici
		Graphs.addAllVertices(grafo, listaNerc);
		
		//Aggiungiamo gli archi
		List<Adiacenza> listaArchi = new ArrayList<>();
		listaArchi = dao.loadAllAdiacenze(idMapNerc);
		
		for (Adiacenza ad : listaArchi) {
			//System.out.println(ad);
			Graphs.addEdge(grafo, ad.getNerc1(), ad.getNerc2(), ad.getWeight());
		}
		
	}
	
	public List<Adiacenza> trovaVicini (Nerc nerc) {
		List<Adiacenza> adiacenti = new ArrayList<>();
		List<Nerc> vicini = new ArrayList<>();
		vicini = Graphs.neighborListOf(grafo, nerc);
		
		
		if (vicini.size() == 0) {
			return null;
		}
		
		for (Nerc vicino : vicini) {
			Adiacenza ad = new Adiacenza (nerc, vicino, grafo.getEdgeWeight(grafo.getEdge(nerc, vicino)));
			adiacenti.add(ad);
		}
		
		Collections.sort(adiacenti);
		
		return adiacenti;
	}

	
	public void simula() {
		Simulatore sim = new Simulatore();
		sim.init(grafo, idMapNerc);
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Nerc> getListaNerc() {
		return listaNerc;
	}
	
	
	
}
